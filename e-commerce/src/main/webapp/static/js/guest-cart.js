/**
 * Guest Cart — localStorage-based cart for unauthenticated users.
 *
 * Storage format (key: "guestCart"):
 *   [{ productId: Number, name: String, price: Number, image: String, quantity: Number }, ...]
 */
(function () {

    const STORAGE_KEY = 'guestCart';

    // ─── Core helpers ───────────────────────────────────────────────

    function getGuestCart() {
        try {
            return JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];
        } catch (_) {
            return [];
        }
    }

    function saveGuestCart(cart) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(cart));
    }

    function getGuestCartCount() {
        return getGuestCart().reduce((sum, item) => sum + item.quantity, 0);
    }

    function clearGuestCart() {
        localStorage.removeItem(STORAGE_KEY);
    }

    // ─── Cart operations ────────────────────────────────────────────

    function addToGuestCart(productId, name, price, image) {
        const cart = getGuestCart();
        const existing = cart.find(i => i.productId === productId);

        if (existing) {
            existing.quantity++;
        } else {
            cart.push({ productId, name, price: parseFloat(price), image, quantity: 1 });
        }

        saveGuestCart(cart);
        updateGuestCartBadge();
        showToast('Delicious choice added!');
    }

    function updateGuestCartQty(productId, direction) {
        const cart = getGuestCart();
        const idx = cart.findIndex(i => i.productId === productId);
        if (idx === -1) return;

        if (direction === 'plus') {
            cart[idx].quantity++;
        } else if (direction === 'minus') {
            cart[idx].quantity--;
            if (cart[idx].quantity <= 0) {
                cart.splice(idx, 1);
            }
        }

        saveGuestCart(cart);
        updateGuestCartBadge();
        return cart;
    }

    function removeFromGuestCart(productId) {
        let cart = getGuestCart();
        cart = cart.filter(i => i.productId !== productId);
        saveGuestCart(cart);
        updateGuestCartBadge();
        return cart;
    }

    // ─── Badge ──────────────────────────────────────────────────────

    function updateGuestCartBadge() {
        const badge = document.getElementById('guest-cart-badge');
        if (!badge) return;

        const count = getGuestCartCount();
        if (count > 0) {
            badge.style.display = 'inline-block';
            badge.textContent = count;
        } else {
            badge.style.display = 'none';
        }
    }

    // ─── Toast ──────────────────────────────────────────────────────

    function showToast(message) {
        const toastEl = document.getElementById('cartToast');
        const toastMsgEl = document.getElementById('cartToastMessage');
        if (toastEl && toastMsgEl) {
            toastMsgEl.textContent = message;
            const toast = new bootstrap.Toast(toastEl);
            toast.show();
        }
    }

    // ─── Featured products add buttons (home page) ──────────────────

    document.querySelectorAll('.guest-add-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const id = parseInt(this.dataset.id);
            const name = this.dataset.name;
            const price = this.dataset.price;
            const image = this.dataset.image;
            addToGuestCart(id, name, price, image);
        });
    });

    // ─── Product list add buttons (guest-sweets page) ───────────────

    document.querySelectorAll('.guest-add-to-cart-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const id = parseInt(this.dataset.id);
            const name = this.dataset.name;
            const price = this.dataset.price;
            const image = this.dataset.image;
            addToGuestCart(id, name, price, image);
        });
    });

    // ─── Cart page rendering ────────────────────────────────────────

    function renderGuestCartPage() {
        const cartContainer = document.getElementById('guest-cart-items');
        const summaryContainer = document.getElementById('guest-cart-summary');
        const emptyCart = document.getElementById('guest-empty-cart');
        const filledCart = document.getElementById('guest-filled-cart');

        if (!cartContainer) return; // Not on cart page

        const cart = getGuestCart();

        if (cart.length === 0) {
            if (emptyCart) {
                emptyCart.classList.add('d-flex');
                emptyCart.style.display = '';
            }
            if (filledCart) filledCart.style.display = 'none';
            return;
        }

        if (emptyCart) {
            emptyCart.classList.remove('d-flex');
            emptyCart.style.display = 'none';
        }
        if (filledCart) filledCart.style.display = '';

        const ctx = typeof CONTEXT_PATH !== 'undefined' ? CONTEXT_PATH : '';

        let cartHtml = '';
        let summaryHtml = '';
        let grandTotal = 0;

        cart.forEach(item => {
            const lineTotal = (item.price * item.quantity).toFixed(2);
            grandTotal += parseFloat(lineTotal);

            cartHtml += `
                <div class="card border rounded-3 p-3 shadow-sm" data-product-id="${item.productId}">
                    <div class="d-flex gap-4">
                        <div class="flex-shrink-0">
                            <img src="${ctx}${item.image}" alt="${item.name}"
                                 class="rounded-3 object-cover" style="width: 100px; height: 100px;">
                        </div>
                        <div class="flex-grow-1 d-flex flex-column justify-content-between">
                            <div class="d-flex justify-content-between">
                                <div>
                                    <span class="fw-semibold text-dark">${item.name}</span>
                                    <p class="text-brand fw-bold mb-0">$${lineTotal}</p>
                                </div>
                            </div>
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="d-flex align-items-center rounded-3 border" style="width: fit-content;">
                                    <button class="btn btn-sm border-0 px-2 py-1 text-muted hover-dark guest-qty-btn"
                                            data-product-id="${item.productId}" data-direction="minus">
                                        <i class="bi bi-dash"></i>
                                    </button>
                                    <span class="px-3 fw-medium">${item.quantity}</span>
                                    <button class="btn btn-sm border-0 px-2 py-1 text-muted hover-dark guest-qty-btn"
                                            data-product-id="${item.productId}" data-direction="plus">
                                        <i class="bi bi-plus"></i>
                                    </button>
                                </div>
                                <button class="btn btn-light btn-sm text-danger rounded-3 p-2 border-0 guest-remove-btn"
                                        data-product-id="${item.productId}" aria-label="Remove item">
                                    <i class="bi bi-trash3 fs-6"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>`;

            summaryHtml += `
                <div class="d-flex justify-content-between small text-muted mb-2">
                    <span>${item.name} x${item.quantity}</span>
                    <span class="text-dark">$${lineTotal}</span>
                </div>`;
        });

        cartContainer.innerHTML = cartHtml;

        if (summaryContainer) {
            summaryContainer.innerHTML = summaryHtml;
        }

        const grandTotalEl = document.getElementById('guest-grand-total');
        if (grandTotalEl) grandTotalEl.textContent = grandTotal.toFixed(2);

        // Bind qty buttons
        cartContainer.querySelectorAll('.guest-qty-btn').forEach(btn => {
            btn.addEventListener('click', function () {
                const pid = parseInt(this.dataset.productId);
                const dir = this.dataset.direction;
                updateGuestCartQty(pid, dir);
                renderGuestCartPage();
            });
        });

        // Bind remove buttons
        cartContainer.querySelectorAll('.guest-remove-btn').forEach(btn => {
            btn.addEventListener('click', function () {
                const pid = parseInt(this.dataset.productId);
                removeFromGuestCart(pid);
                renderGuestCartPage();
            });
        });
    }

    // ─── Sweets page filter/search (for guest-sweets.jsp) ───────────

    const items = document.querySelectorAll('.product-item');
    const filterBtns = document.querySelectorAll('.filter-btn');
    const searchInput = document.getElementById('searchInput');

    if (filterBtns.length > 0 && searchInput) {
        let activeCategory = 'all';

        const ACTIVE_CLASSES = ['btn-primary', 'bg-brand', 'border-0'];
        const INACTIVE_CLASSES = ['btn-outline-secondary', 'bg-white', 'text-dark'];

        function setActiveBtn(activeBtn) {
            filterBtns.forEach(btn => {
                btn.classList.remove(...ACTIVE_CLASSES);
                btn.classList.add(...INACTIVE_CLASSES);
            });
            activeBtn.classList.remove(...INACTIVE_CLASSES);
            activeBtn.classList.add(...ACTIVE_CLASSES);
        }

        function applyFilters() {
            const keyword = searchInput.value.trim().toLowerCase();
            items.forEach(item => {
                const category = item.dataset.category?.toLowerCase() ?? '';
                const name = item.querySelector('h3')?.textContent.toLowerCase() ?? '';
                const matchesCategory = activeCategory === 'all' || category === activeCategory.toLowerCase();
                const matchesSearch = keyword === '' || name.includes(keyword);
                item.style.display = (matchesCategory && matchesSearch) ? '' : 'none';
            });
        }

        filterBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                setActiveBtn(btn);
                activeCategory = btn.dataset.filter;
                applyFilters();
            });
        });

        searchInput.addEventListener('input', applyFilters);
    }

    // ─── Initialization ─────────────────────────────────────────────

    document.addEventListener('DOMContentLoaded', () => {
        updateGuestCartBadge();
        renderGuestCartPage();
    });

    // Expose for external use (login-merge.js)
    window.GuestCart = {
        get: getGuestCart,
        clear: clearGuestCart,
        count: getGuestCartCount
    };

})();
