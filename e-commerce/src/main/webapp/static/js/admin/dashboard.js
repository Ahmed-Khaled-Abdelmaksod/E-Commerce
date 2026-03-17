document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const initialTab = urlParams.get('tab') || 'products';
    loadTab(initialTab);
});

function loadTab(tabName) {
    const contentDiv = document.getElementById('dashboard-content');
    if (!contentDiv) return;
    contentDiv.innerHTML = '<div class="text-center mt-5"><div class="spinner-border"></div></div>';

    var xhr = new XMLHttpRequest();
    // Dynamic URL for any context path
    xhr.open('GET', window.location.pathname + '?tab=' + tabName, true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            contentDiv.innerHTML = xhr.responseText;
            // Update URL without refresh
            const newUrl = window.location.pathname + '?tab=' + tabName;
            window.history.pushState({path: newUrl}, '', newUrl);
        }
    };
    xhr.send();
}

// --- GLOBAL DELETE LOGIC ---
function confirmDelete(productId, productName) {
    document.getElementById('deleteProductName').innerText = productName;
    document.getElementById('deleteProductId').value = productId;
}

function executeDeleteAjax(event) {
    event.preventDefault();
    const productId = document.getElementById('deleteProductId').value;

    // Resolve context path dynamically
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
                bootstrap.Modal.getInstance(document.getElementById('deleteProductModal')).hide();
                const row = document.getElementById('product-row-' + productId);
                if (row) {
                    row.style.opacity = '0';
                    setTimeout(() => row.remove(), 400);
                }
            } else {
                alert("Error: " + data.message);
            }
        })
        .catch(err => alert("Communication error with server."));
}