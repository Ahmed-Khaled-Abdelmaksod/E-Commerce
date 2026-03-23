(function () {
    const items = document.querySelectorAll('.product-item');
    const filterBtns = document.querySelectorAll('.filter-btn');
    const searchInput = document.getElementById('searchInput');

    let activeCategory = 'all';

    // Classes that define active vs inactive state (shape/radius classes are NEVER toggled)
    const ACTIVE_CLASSES   = ['btn-primary', 'bg-brand', 'border-0'];
    const INACTIVE_CLASSES = ['btn-outline-secondary', 'bg-white', 'text-dark'];

    function setActiveBtn(activeBtn) {
        filterBtns.forEach(btn => {
            // Reset every button to inactive
            btn.classList.remove(...ACTIVE_CLASSES);
            btn.classList.add(...INACTIVE_CLASSES);
        });
        // Apply active style to the clicked button
        activeBtn.classList.remove(...INACTIVE_CLASSES);
        activeBtn.classList.add(...ACTIVE_CLASSES);
    }

    function applyFilters() {
        const keyword = searchInput.value.trim().toLowerCase();

        items.forEach(item => {
            const category = item.dataset.category?.toLowerCase() ?? '';
            const name     = item.querySelector('h3')?.textContent.toLowerCase() ?? '';

            const matchesCategory = activeCategory === 'all' || category === activeCategory.toLowerCase();
            const matchesSearch   = keyword === '' || name.includes(keyword);

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
    document.querySelectorAll('.add-to-cart-btn').forEach(button => {
        button.addEventListener('click', function() {
            // 1. Get the ID from the data attribute
            const productId = this.getAttribute('data-id');

            // 2. Send it to your Servlet via AJAX (Fetch API)
            addToCart(productId);
        });
    });

    function addToCart(id) {
        const url = (typeof CONTEXT_PATH !== 'undefined' ? CONTEXT_PATH : '') + '/user/cart/add?productId=' + id;
        fetch(url, { method: 'POST' })
            .then(response => {
                if(!response.ok) {
                    throw new Error("HTTP error " + response.status);
                }
                return response.json();
            })
            .then(data => {
                if(data.status === "ADDED") {
                    updateCartBadge(data.newCount);
                    showToast("Delicious choice added!");
                } else if(data.status === "LOWQUNATITY") {
                    showToast("Sorry, we're almost out of these!");
                } else if (data.status === "INSUFFICIENT_CREDIT") {
                    showToast("Insufficient credit balance.");
                }
            })
            .catch(error => {
                console.error("Error adding to cart:", error);
                showToast("Error adding to cart!");
            });
    }

    function showToast(message) {
        const toastEl = document.getElementById('cartToast');
        const toastMessageEl = document.getElementById('cartToastMessage');
        if (toastEl && toastMessageEl) {
            toastMessageEl.textContent = message;
            // Assuming bootstrap is available globally
            const toast = new bootstrap.Toast(toastEl);
            toast.show();
        }
    }

    function updateCartBadge(count) {
        const badge = document.getElementById('cart-badge');
        if (badge) {
            if (count > 0) {
                badge.style.display = 'inline-block';
                badge.textContent = count;
            } else {
                badge.style.display = 'none';
            }
        }
    }
})();
