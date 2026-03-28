/**
 * login-merge.js  — Merges the guest localStorage cart into the server-side
 * DB cart immediately after a successful login or signup.
 *
 * Include this script on the sign-in and sign-up result pages (or on the
 * post-login redirect page).
 *
 * It checks for a `mergeCart=true` flag in the URL (set by AuthController /
 * RegisterController after detecting a guest cart). When present it POSTs the
 * guest cart items to `/cart/merge`, clears localStorage, then redirects to
 * the appropriate destination (checkout or user home).
 */
(function () {

    const params = new URLSearchParams(window.location.search);

    if (params.get('mergeCart') !== 'true') return; // nothing to do

    // Read guest cart from localStorage
    let guestCart;
    try {
        guestCart = JSON.parse(localStorage.getItem('guestCart')) || [];
    } catch (_) {
        guestCart = [];
    }

    if (guestCart.length === 0) {
        // No items to merge — redirect immediately
        redirectAfterMerge();
        return;
    }

    // Build the merge payload
    const payload = guestCart.map(item => ({
        productId: item.productId,
        quantity: item.quantity
    }));

    const ctx = typeof CONTEXT_PATH !== 'undefined' ? CONTEXT_PATH : '';

    fetch(ctx + '/cart/merge', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
        credentials: 'same-origin'
    })
        .then(resp => resp.json())
        .then(data => {
            // Clear guest cart regardless of merge result
            localStorage.removeItem('guestCart');
            redirectAfterMerge();
        })
        .catch(err => {
            console.error('Cart merge failed:', err);
            // Still clear and redirect so user isn't stuck
            localStorage.removeItem('guestCart');
            redirectAfterMerge();
        });

    function redirectAfterMerge() {
        const ctx = typeof CONTEXT_PATH !== 'undefined' ? CONTEXT_PATH : '';
        const wantsCheckout = params.get('checkout') === 'true';
        window.location.href = ctx + (wantsCheckout ? '/checkout' : '/user/home');
    }

})();
