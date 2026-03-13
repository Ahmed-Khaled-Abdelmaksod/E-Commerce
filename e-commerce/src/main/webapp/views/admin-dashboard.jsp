<%--
  Created by IntelliJ IDEA.
  User: mosal
  Date: 3/6/2026
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sweet Delights - Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css" />
</head>

<body>

<nav class="navbar navbar-expand-lg sticky-top">
    <div class="container">
        <a class="navbar-brand fw-bold fs-4 text-brand" href="index.jsp">
            <span class="avatar-circle d-inline-flex me-2 text-white" style="background-color: #d49a89;">S</span>
            Sweet Delights
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="shop.jsp">Shop</a></li>
                <li class="nav-item"><a class="nav-link active fw-bold text-brand" href="admin">Dashboard</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="header-section">
    <div class="container">
        <h1 class="fw-bold text-brand" style="font-family: Georgia, serif;">Admin Dashboard</h1>
        <p class="text-muted mb-0">Manage your sweet shop</p>
    </div>
</div>

<div class="container mt-4 mb-5">
    <ul class="nav nav-tabs" id="adminTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="products-tab" data-bs-toggle="tab" data-bs-target="#products" type="button" role="tab">
                <i class="bi bi-box-seam me-2"></i>Products
            </button>
        </li>
    </ul>

    <div class="tab-content" id="adminTabsContent">
        <div class="tab-pane fade show active" id="products" role="tabpanel">
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
                            <td class="text-muted">${product.category.name}</td>
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
        </div>
    </div>
</div>

<div class="modal fade" id="addProductModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header border-bottom-0">
                <h5 class="modal-title fw-bold text-brand">Add New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="addProduct" method="POST" enctype="multipart/form-data">
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
                    <form action="editProduct" method="POST" enctype="multipart/form-data">
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
                                    <option value="${cat.categoryId}" ${cat.categoryId == product.category.categoryId ? 'selected' : ''}>${cat.name}</option>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function confirmDelete(productId, productName) {
        document.getElementById('deleteProductName').innerText = productName;
        document.getElementById('deleteProductId').value = productId;
    }

    function executeDeleteAjax(event) {
        event.preventDefault();
        const productId = document.getElementById('deleteProductId').value;

        fetch('deleteProduct', {
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
</script>
</body>
</html>