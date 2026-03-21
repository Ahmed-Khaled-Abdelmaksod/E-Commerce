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
})();
