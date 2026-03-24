/**
 * Admin Dashboard Core Logic - Fixed Version
 */

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const initialTab = urlParams.get('tab') || 'products';
    loadTab(initialTab);
});

function loadTab(tabName) {
    setActiveTabUI(tabName);
    const contentDiv = document.getElementById('dashboard-content');
    if (!contentDiv) return;

    contentDiv.innerHTML = `
        <div class="text-center mt-5">
            <div class="spinner-border" style="color: #df6c7f;" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        </div>`;

    const xhr = new XMLHttpRequest();

    // Use current path without query string
    const cleanPath = window.location.origin + window.location.pathname;

    xhr.open('GET', cleanPath + '?tab=' + tabName, true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                contentDiv.innerHTML = xhr.responseText;

                // Re-execute scripts injected via innerHTML (they do not run automatically)
                contentDiv.querySelectorAll('script').forEach(function(oldScript) {
                    var newScript = document.createElement('script');
                    newScript.textContent = oldScript.textContent;
                    document.body.appendChild(newScript);
                    document.body.removeChild(newScript);
                });

                const newUrl = cleanPath + '?tab=' + tabName;
                window.history.pushState({path: newUrl}, '', newUrl);

            } else {
                contentDiv.innerHTML =
                    '<div class="alert alert-danger m-3">Error loading data. Status: ' + xhr.status + '</div>';
            }
        }
    };

    xhr.send();
}

function setActiveTabUI(tabName) {
    document.querySelectorAll('.custom-tab').forEach(tab => tab.classList.remove('active'));
    const activeTab = document.getElementById('tab-' + tabName);
    if (activeTab) activeTab.classList.add('active');
}

// Toggle form for adding a new product
function toggleProductForm() {
    const container = document.getElementById('productFormContainer');
    if (!container) return;

    if (container.classList.contains('d-none')) {
        resetProductForm(); // Reset form when opening for adding
        container.classList.remove('d-none');
        window.scrollTo({ top: 0, behavior: 'smooth' });
    } else {
        container.classList.add('d-none');
    }
}

// Reset product form to default (Add mode)
function resetProductForm() {
    const form = document.getElementById('productActionForm');
    if(form) {
        form.reset();

        // Reset form action to Add Product
        const ctx = window.location.pathname.substring(0, window.location.pathname.indexOf('/admin'));
        form.action = ctx + "/addProduct";
    }

    document.getElementById('formTitle').innerText = "New Product";
    document.getElementById('submitBtnText').innerText = "Add Product";
    document.getElementById('formProductId').value = "";
}

// Handle edit button click
function handleEditClick(btn) {
    const container = document.getElementById('productFormContainer');
    if(container) container.classList.remove('d-none');

    window.scrollTo({ top: 0, behavior: 'smooth' });

    document.getElementById('formTitle').innerText = "Edit Product";
    document.getElementById('submitBtnText').innerText = "Update Product";

    // Set correct action for edit
    const ctx = window.location.pathname.substring(0, window.location.pathname.indexOf('/admin'));
    document.getElementById('productActionForm').action = ctx + "/editProduct";

    // Fill form with product data
    document.getElementById('formProductId').value = btn.getAttribute('data-id');
    document.getElementById('formName').value = btn.getAttribute('data-name');
    document.getElementById('formDescription').value = btn.getAttribute('data-desc');
    document.getElementById('formPrice').value = btn.getAttribute('data-price');
    document.getElementById('formQuantity').value = btn.getAttribute('data-stock');
    document.getElementById('formCategory').value = btn.getAttribute('data-catid');

    const highlighted = btn.getAttribute('data-high');
    document.getElementById('formHighlighted').checked = (highlighted === 'true');
}

// Prepare delete confirmation modal
function confirmDelete(productId, productName) {
    const idInput = document.getElementById('deleteProductId');
    const nameSpan = document.getElementById('deleteProductName');

    if (idInput) idInput.value = productId;
    if (nameSpan) nameSpan.innerText = productName;
}

// Execute delete request via AJAX
function executeDeleteAjax(event) {
    if (event) event.preventDefault();

    const productId = document.getElementById('deleteProductId').value;
    const ctx = window.location.pathname.substring(0, window.location.pathname.indexOf('/admin'));

    fetch(ctx + '/deleteProduct', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'productId=' + encodeURIComponent(productId)
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 'success') {
            bootstrap.Modal
                .getInstance(document.getElementById('deleteProductModal'))
                .hide();

            const row = document.getElementById('product-row-' + productId);
            if (row) row.remove();
        }
    });
}