<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Sweet Delights</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=DM+Serif+Display:ital@0;1&display=swap" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link href="${pageContext.request.contextPath}/static/css/globals.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/admin/dashboard.css" rel="stylesheet">
</head>
<body>

    <jsp:include page="/static/html/admin-header.jsp" />

    <%-- Dashboard Header --%>
    <div class="dash-header-bg py-5">
        <div class="container">
            <h1 class="font-serif fw-bold mb-1" style="font-size: 2.5rem; letter-spacing: -1px; color: var(--foreground);">Admin Dashboard</h1>
            <p class="mb-0 text-muted-custom" style="font-size: 0.95rem;">Manage your sweet shop</p>
        </div>
    </div>

    <div class="container mt-5">

        <%-- Tabs --%>
        <div class="custom-tabs-container">
            <span id="tab-products" class="custom-tab active" onclick="loadTab('products')">
                <i class="bi bi-box-seam"></i> Products
            </span>
            <span id="tab-customers" class="custom-tab" onclick="loadTab('customers')">
                <i class="bi bi-people"></i> Customers
            </span>
            <span id="tab-orders" class="custom-tab" onclick="loadTab('orders')">
                <i class="bi bi-receipt"></i> Orders
            </span>
        </div>

        <%-- Dynamic Content --%>
        <div id="dashboard-content"></div>

    </div>

    <%-- Delete Product Modal --%>
    <div class="modal fade" id="deleteProductModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content shadow">
                <div class="modal-header border-0 pt-4 px-4">
                    <h5 class="fw-bold mb-0" style="font-family: var(--font-sans);">Delete Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body px-4 pb-2">
                    <p class="text-muted-custom mb-0">
                        Are you sure you want to delete <strong id="deleteProductName" style="color: var(--foreground);"></strong>?
                        This action cannot be undone.
                    </p>
                    <input type="hidden" id="deleteProductId">
                </div>
                <div class="modal-footer border-0 pb-4 px-4 gap-2">
                    <button type="button" class="btn btn-light px-4" data-bs-dismiss="modal"
                            style="border-radius: 0.5rem; font-family: var(--font-sans); font-weight: 500;">
                        Cancel
                    </button>
                    <button type="button" class="btn btn-danger px-4" onclick="executeDeleteAjax(event)"
                            style="border-radius: 0.5rem; font-family: var(--font-sans); font-weight: 500; background-color: #dc3545; border: none;">
                        Delete Now
                    </button>
                </div>
            </div>
        </div>
    </div>

    <%-- Add Category Modal --%>
    <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content shadow">
                <div class="modal-header border-0 pt-4 px-4">
                    <h5 class="fw-bold mb-0" style="font-family: var(--font-sans);">New Category</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body px-4">
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Category Name</label>
                        <input type="text" id="newCategoryName" class="form-control"
                               style="background-color: var(--muted); border: 1px solid var(--border); border-radius: 0.5rem; font-family: var(--font-sans);">
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Description</label>
                        <textarea id="newCategoryDesc" class="form-control" rows="2"
                                  style="background-color: var(--muted); border: 1px solid var(--border); border-radius: 0.5rem; font-family: var(--font-sans);"></textarea>
                    </div>
                </div>
                <div class="modal-footer border-0 pb-4 px-4">
                    <button type="button" class="btn px-4 btn-primary-custom" onclick="ajaxAddCategory()">
                        Save Category
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/admin/dashboard.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function ajaxAddCategory() {
            const name = document.getElementById('newCategoryName').value;
            const desc = document.getElementById('newCategoryDesc').value;
            const ctx = "${pageContext.request.contextPath}";

            if (!name.trim()) { alert("Name required"); return; }

            fetch(ctx + '/addCategory', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'categoryName=' + encodeURIComponent(name) + '&categoryDesc=' + encodeURIComponent(desc)
            })
            .then(res => res.json())
            .then(data => {
                if (data.status === 'success') {
                    document.querySelectorAll('.category-dropdown').forEach(dropdown => {
                        const option = new Option(data.name, data.id);
                        dropdown.add(option);
                    });
                    bootstrap.Modal.getOrCreateInstance(document.getElementById('addCategoryModal')).hide();
                    document.getElementById('newCategoryName').value = '';
                    document.getElementById('newCategoryDesc').value = '';
                }
            })
            .catch(err => console.error(err));
        }
    </script>
</body>
</html>
