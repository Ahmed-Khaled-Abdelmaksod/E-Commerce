<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4 mt-3">
    <h4 class="text-brand fw-bold mb-0">Products</h4>
    <button class="btn btn-brand" data-bs-toggle="modal" data-bs-target="#addProductModal">
        <i class="bi bi-plus-lg me-1"></i> Add Product
    </button>
</div>

<div class="table-responsive">
    <table class="table table-custom table-borderless">
        <thead class="text-muted small border-bottom">
        <tr>
            <th>Product</th>
            <th>Category</th>
            <th>Price</th>
            <th>Quantity</th>
            <th class="text-end">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr id="product-row-${product.productId}">
                <td>
                    <div class="d-flex align-items-center gap-3">
                        <img src="${pageContext.request.contextPath}${product.imageUrl}" class="rounded product-img-thumbnail" alt="Product Image" style="width: 50px; height: 50px; object-fit: cover;">
                        <span class="fw-medium">${product.name}</span>
                    </div>
                </td>
                <td class="text-muted">${product.categoryName}</td>
                <td class="fw-medium">$${product.price}</td>
                <td><span class="stock-badge">${product.stockQuantity}</span></td>
                <td class="text-end">
                    <button class="btn btn-sm btn-light me-1" data-bs-toggle="modal" data-bs-target="#editProductModal${product.productId}">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-light text-danger" onclick="confirmDelete('${product.productId}', '${product.name}')" data-bs-toggle="modal" data-bs-target="#deleteProductModal">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty products}">
            <tr><td colspan="5" class="text-center text-muted py-4">No products found in the database.</td></tr>
        </c:if>
        </tbody>
    </table>
</div>

<div class="modal fade" id="addProductModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header border-bottom-0">
                <h5 class="modal-title fw-bold text-brand">Add New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/addProduct" method="POST" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label class="form-label small fw-medium text-brand">Product Name</label>
                        <input type="text" class="form-control form-control-sm py-2" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium text-brand">Description</label>
                        <textarea class="form-control form-control-sm py-2" name="description" rows="3" required></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label small fw-medium text-brand">Price ($)</label>
                            <input type="number" step="0.01" class="form-control form-control-sm py-2" name="price" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label small fw-medium text-brand">Quantity</label>
                            <input type="number" class="form-control form-control-sm py-2" name="quantity" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium text-brand">Category</label>
                        <select class="form-select form-select-sm py-2" name="categoryId" required>
                            <option value="" disabled selected>Select a category...</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}">${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-4">
                        <label class="form-label small fw-medium text-brand">Product Image</label>
                        <input type="file" class="form-control form-control-sm" name="image" accept="image/*" required>
                    </div>
                    <div class="d-flex justify-content-end gap-2">
                        <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-brand">Add Product</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<c:forEach var="product" items="${products}">
    <div class="modal fade" id="editProductModal${product.productId}" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header border-bottom-0">
                    <h5 class="modal-title fw-bold text-brand">Edit Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/editProduct" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <div class="mb-3">
                            <label class="form-label small fw-medium">Product Name</label>
                            <input type="text" class="form-control form-control-sm py-2" name="name" value="${product.name}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-medium text-brand">Description</label>
                            <textarea class="form-control form-control-sm py-2" name="description" rows="3" required>${product.description}</textarea>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label small fw-medium">Price ($)</label>
                                <input type="number" step="0.01" class="form-control form-control-sm py-2" name="price" value="${product.price}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label small fw-medium">Quantity</label>
                                <input type="number" class="form-control form-control-sm py-2" name="quantity" value="${product.stockQuantity}" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-medium">Category</label>
                            <select class="form-select form-select-sm py-2" name="categoryId" required>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.categoryId}" ${cat.categoryId == product.categoryId ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-4">
                            <label class="form-label small fw-medium text-brand">Update Image (Optional)</label>
                            <input type="file" class="form-control form-control-sm" name="image" accept="image/*">
                        </div>
                        <div class="d-flex justify-content-end gap-2">
                            <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-brand">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<div class="modal fade" id="deleteProductModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-sm">
        <div class="modal-content text-center p-3">
            <div class="modal-body">
                <i class="bi bi-exclamation-circle text-danger fs-1 mb-3"></i>
                <h5 class="fw-bold mb-2">Delete Product?</h5>
                <p class="text-muted small mb-4">Are you sure you want to delete <strong id="deleteProductName" class="text-dark"></strong>? This action cannot be undone.</p>
                <form id="deleteForm" onsubmit="executeDeleteAjax(event)">
                    <input type="hidden" id="deleteProductId" name="productId" value="">
                    <div class="d-flex justify-content-center gap-2">
                        <button type="button" class="btn btn-light w-50" data-bs-dismiss="modal">No, Keep It</button>
                        <button type="submit" class="btn btn-danger w-50">Yes, Delete</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function confirmDelete(productId, productName) {
        document.getElementById('deleteProductName').innerText = productName;
        document.getElementById('deleteProductId').value = productId;
    }

    function executeDeleteAjax(event) {
        event.preventDefault();
        const productId = document.getElementById('deleteProductId').value;

        // Grab context path safely from the page
        const ctx = "${pageContext.request.contextPath}";

        // FIXED: Added Context Path to fetch URL
        fetch(ctx + '/deleteProduct', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'productId=' + encodeURIComponent(productId)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    const modalElement = document.getElementById('deleteProductModal');
                    const modalInstance = bootstrap.Modal.getInstance(modalElement);
                    modalInstance.hide();

                    const rowToTrash = document.getElementById('product-row-' + productId);
                    if (rowToTrash) {
                        rowToTrash.style.transition = "opacity 0.5s ease";
                        rowToTrash.style.opacity = 0;
                        setTimeout(() => rowToTrash.remove(), 500);
                    }
                } else {
                    alert("Error deleting product.");
                }
            })
            .catch(error => console.error('Error:', error));
    }

    // Manual initialization for AJAX loaded content
    document.querySelectorAll('[data-bs-toggle="modal"]').forEach(el => {
        new bootstrap.Modal(document.querySelector(el.getAttribute('data-bs-target')));
    });
</script>