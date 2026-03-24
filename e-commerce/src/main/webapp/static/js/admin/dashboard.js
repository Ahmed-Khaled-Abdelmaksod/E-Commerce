/**
 * Admin Dashboard Core Logic
 * Handles Tab Switching, Inline Form Toggling, and AJAX CRUD operations.
 */

document.addEventListener("DOMContentLoaded", function() {
    // جلب التبويب الحالي من الرابط أو البدء بـ products كافتراضي
    const urlParams = new URLSearchParams(window.location.search);
    const initialTab = urlParams.get('tab') || 'products';
    loadTab(initialTab);
});

/**
 * تبديل التبويبات باستخدام AJAX لضمان سرعة الاستجابة
 */
function loadTab(tabName) {
    setActiveTabUI(tabName);

    const contentDiv = document.getElementById('dashboard-content');
    if (!contentDiv) return;
    
    // إظهار مؤشر تحميل (Spinner)
    contentDiv.innerHTML = `
        <div class="text-center mt-5">
            <div class="spinner-border" style="color: #df6c7f;" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        </div>`;

    const xhr = new XMLHttpRequest();
    // استخدام window.location.pathname لضمان المسار الصحيح للـ Servlet
    xhr.open('GET', window.location.pathname + '?tab=' + tabName, true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                contentDiv.innerHTML = xhr.responseText;
                // تحديث الرابط في المتصفح بدون إعادة تحميل الصفحة
                const newUrl = window.location.pathname + '?tab=' + tabName;
                window.history.pushState({path: newUrl}, '', newUrl);
            } else {
                contentDiv.innerHTML = '<div class="alert alert-danger m-3">Error loading data. Please try again.</div>';
            }
        }
    };
    xhr.send();
}

/**
 * تحديث شكل التبويب النشط في القائمة
 */
function setActiveTabUI(tabName) {
    document.querySelectorAll('.custom-tab').forEach(tab => tab.classList.remove('active'));
    const activeTab = document.getElementById('tab-' + tabName);
    if (activeTab) {
        activeTab.classList.add('active');
    }
}

/**
 * منطق الحذف باستخدام AJAX (بدون إعادة تحميل الصفحة)
 */
function executeDeleteAjax(event) {
    event.preventDefault();
    
    const productId = document.getElementById('deleteProductId').value;
    // استنتاج الـ Context Path ديناميكياً
    const currentPath = window.location.pathname;
    const basePath = currentPath.substring(0, currentPath.lastIndexOf('/admin'));

    fetch(basePath + '/deleteProduct', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'productId=' + encodeURIComponent(productId)
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 'success') {
            // إغلاق نافذة التأكيد (Modal)
            const modalEl = document.getElementById('deleteProductModal');
            const modalInstance = bootstrap.Modal.getInstance(modalEl);
            if (modalInstance) modalInstance.hide();

            // حذف الصف من الجدول بتأثير اختفاء تدريجي (Fade out)
            const row = document.getElementById('product-row-' + productId);
            if (row) {
                row.style.transition = 'all 0.4s ease';
                row.style.opacity = '0';
                row.style.transform = 'translateX(20px)';
                setTimeout(() => row.remove(), 400);
            }
        } else {
            alert("Error: " + (data.message || "Could not delete product."));
        }
    })
    .catch(err => {
        console.error("Delete Error:", err);
        alert("Communication error with server.");
    });
}

/**
 * منطق إضافة تصنيف جديد عبر AJAX (لتحديث القوائم المنسدلة فوراً)
 */
function ajaxAddCategory() {
    const name = document.getElementById('newCategoryName').value;
    const desc = document.getElementById('newCategoryDesc').value;
    
    if (!name.trim()) {
        alert("Category name is required.");
        return;
    }

    const currentPath = window.location.pathname;
    const basePath = currentPath.substring(0, currentPath.lastIndexOf('/admin'));

    fetch(basePath + '/addCategory', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `categoryName=${encodeURIComponent(name)}&categoryDesc=${encodeURIComponent(desc)}`
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === 'success') {
            // تحديث كل الـ Dropdowns الموجودة في الصفحة بالخيار الجديد
            document.querySelectorAll('.category-dropdown, #formCategory').forEach(dropdown => {
                const option = new Option(data.name, data.id);
                dropdown.add(option);
            });

            // إغلاق الـ Modal وإعادة ضبط المدخلات
            const catModalEl = document.getElementById('addCategoryModal');
            bootstrap.Modal.getOrCreateInstance(catModalEl).hide();
            document.getElementById('newCategoryName').value = '';
            document.getElementById('newCategoryDesc').value = '';
        }
    })
    .catch(err => console.error("Add Category Error:", err));
}