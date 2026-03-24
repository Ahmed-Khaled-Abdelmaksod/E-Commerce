// Cart UI helpers (used by `views/cart.jsp`)
function updateCartBadge(count) {
    const badge = document.getElementById('cart-badge');
    if (!badge) return;

    if (count > 0) {
        badge.style.display = 'inline-block';
        badge.textContent = count;
    } else {
        badge.style.display = 'none';
    }
}

function formatMoney(value) {
    return Number(value).toFixed(2);
}

function updateItemPrices(cartItemId, quantity) {
    const itemEl = document.getElementById('cart-item-' + cartItemId);
    if (!itemEl) return;

    const unitPrice = parseFloat(itemEl.dataset.unitPrice || '0');
    const lineTotal = unitPrice * quantity;

    const lineTotalEl = document.getElementById('line-total-' + cartItemId);
    if (lineTotalEl) lineTotalEl.textContent = formatMoney(lineTotal);

    const summaryQtyEl = document.getElementById('summary-qty-' + cartItemId);
    if (summaryQtyEl) summaryQtyEl.textContent = quantity;

    const summaryLineEl = document.getElementById('summary-line-' + cartItemId);
    if (summaryLineEl) summaryLineEl.textContent = formatMoney(lineTotal);
}

function recalculateGrandTotal() {
    let total = 0;
    document.querySelectorAll('[id^="cart-item-"]').forEach(itemEl => {
        const cartItemId = itemEl.id.replace('cart-item-', '');
        const qtyEl = document.getElementById('qty-' + cartItemId);
        const qty = parseInt(qtyEl?.textContent || '0', 10);
        const unitPrice = parseFloat(itemEl.dataset.unitPrice || '0');
        total += (qty * unitPrice);
    });

    const grandTotalEl = document.getElementById('grand-total');
    if (grandTotalEl) grandTotalEl.textContent = formatMoney(total);
}

function updateQuantity(cartItemId, direction) {
    const url = (typeof CONTEXT_PATH !== 'undefined' ? CONTEXT_PATH : '') + '/user/cart/update';

    // Use x-www-form-urlencoded so the servlet can read with request.getParameter(...)
    const params = new URLSearchParams();
    params.append('cartItemId', cartItemId);
    params.append('direction', direction); // 'plus' | 'minus'

    fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString(),
        credentials: 'same-origin'
    })
        .then(response => {
            if (!response.ok) throw new Error('HTTP error ' + response.status);
            return response.json();
        })
        .then(data => {
            if (!data || data.status === 'NOT_LOGGED_IN') {
                // No cart badge update if user isn't authenticated.
                return;
            }

            updateCartBadge(data.cartCount ?? 0);

            const qtyEl = document.getElementById('qty-' + cartItemId);
            if (qtyEl) qtyEl.textContent = (data.newQuantity ?? 0);
            updateItemPrices(cartItemId, Number(data.newQuantity ?? 0));

            if (data.removed === true) {
                const itemEl = document.getElementById('cart-item-' + cartItemId);
                if (itemEl) itemEl.remove();
                const summaryEl = document.getElementById('summary-item-' + cartItemId);
                if (summaryEl) summaryEl.remove();
            }

            recalculateGrandTotal();

            if (data.message && data.status !== 'REMOVED') {
                // Keep it non-blocking.
                console.log(data.message);
            }
        })
        .catch(err => console.error('Error updating cart quantity:', err));
}

function deleteCartItem(cartItemId) {
    const url = (typeof CONTEXT_PATH !== 'undefined' ? CONTEXT_PATH : '') + '/user/cart/remove';

    const params = new URLSearchParams();
    params.append('cartItemId', cartItemId);

    fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString(),
        credentials: 'same-origin'
    })
        .then(response => {
            if (!response.ok) throw new Error('HTTP error ' + response.status);
            return response.json();
        })
        .then(data => {
            if (!data || data.status === 'NOT_LOGGED_IN') return;

            updateCartBadge(data.cartCount ?? 0);

            if (data.removed === true) {
                const itemEl = document.getElementById('cart-item-' + cartItemId);
                if (itemEl) itemEl.remove();
                const summaryEl = document.getElementById('summary-item-' + cartItemId);
                if (summaryEl) summaryEl.remove();
            }

            if ((data.cartCount ?? 0) === 0) {
                // No items left -> safest way to show empty-cart + recalc totals.
                window.location.reload();
                return;
            }

            recalculateGrandTotal();
        })
        .catch(err => console.error('Error deleting cart item:', err));
}

