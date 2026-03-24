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
    <link rel="stylesheet" href="../static/css/admin-dashboard.css" />
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
                <li class="nav-item"><a class="nav-link active fw-bold text-brand" href="admin.jsp">Dashboard</a></li>
            </ul>
        </div>
        <div class="d-flex align-items-center gap-3">
            <a href="#" class="text-dark"><i class="bi bi-cart fs-5"></i></a>
            <a href="${pageContext.request.contextPath}/admin/profile" class="text-dark"><i class="bi bi-person fs-5"></i></a>
            <a href="${pageContext.request.contextPath}/auth/logout" class="text-dark"><i class="bi bi-box-arrow-right fs-5"></i></a>
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
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="customers-tab" data-bs-toggle="tab" data-bs-target="#customers" type="button" role="tab">
                <i class="bi bi-people me-2"></i>Customers
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="orders-tab" data-bs-toggle="tab" data-bs-target="#orders" type="button" role="tab">
                <i class="bi bi-receipt me-2"></i>Orders
            </button>
        </li>
    </ul>

    <div class="tab-content" id="adminTabsContent">

        <div class="tab-pane fade show active" id="products" role="tabpanel">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h4 class="text-brand fw-bold mb-0">Products (1)</h4>
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
                        <th>Quantity</th> <th class="text-end">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <div class="d-flex align-items-center gap-3">
                                <img src="../static/images/chocolate-cake.jpg" class="rounded product-img-thumbnail" alt="Cupcake">
                                <span class="fw-medium">Strawberry Cupcake</span>
                            </div>
                        </td>
                        <td class="text-muted">Cupcakes</td>
                        <td class="fw-medium">$4.99</td>
                        <td><span class="stock-badge">50</span></td>
                        <td class="text-end">
                            <button class="btn btn-sm btn-light me-1" data-bs-toggle="modal" data-bs-target="#editProductModal">
                                <i class="bi bi-pencil"></i>
                            </button>
                            <button class="btn btn-sm btn-light text-danger" onclick="confirmDelete('1', 'Strawberry Cupcake')" data-bs-toggle="modal" data-bs-target="#deleteProductModal">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="tab-pane fade" id="customers" role="tabpanel">
            <h4 class="text-brand fw-bold mb-4">Customers (1)</h4>
            <div class="row g-4">
                <div class="col-12 col-md-6 col-lg-4">
                    <div class="customer-card">
                        <div class="d-flex align-items-center gap-3 mb-3">
                            <div class="avatar-circle"><i class="bi bi-person fs-4"></i></div>
                            <div>
                                <h6 class="mb-0 fw-bold text-brand">Jane Doe</h6>
                                <small class="text-muted">ID: 1</small>
                            </div>
                        </div>
                        <div class="text-muted small space-y-2">
                            <p class="mb-1"><i class="bi bi-envelope me-2"></i>jane@example.com</p>
                            <p class="mb-1"><i class="bi bi-telephone me-2"></i>555-0200</p>
                            <p class="mb-1"><i class="bi bi-geo-alt me-2"></i>456 Dessert Lane, Sweet City</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="orders" role="tabpanel">
            <h4 class="text-brand fw-bold mb-4">Orders (0)</h4>
            <p class="text-muted">No orders placed yet.</p>
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
                        <input type="text" class="form-control form-control-sm py-2" name="name" placeholder="e.g., Blueberry Cheesecake" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium text-brand">Description</label>
                        <textarea class="form-control form-control-sm py-2" name="description" rows="3" placeholder="Describe the sweet treat..." required></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label small fw-medium text-brand">Price ($)</label>
                            <input type="number" step="0.01" class="form-control form-control-sm py-2" name="price" placeholder="0.00" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label small fw-medium text-brand">Quantity</label>
                            <input type="number" class="form-control form-control-sm py-2" name="quantity" placeholder="0" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium text-brand">Category</label>
                        <select class="form-select form-select-sm py-2" name="categoryId" required>
                            <option value="" disabled selected>Select a category...</option>
                            <option value="1">Cupcakes</option>
                            <option value="2">Cakes</option>
                            <option value="3">Cookies</option>
                            <option value="4">Donuts</option>
                            <option value="5">Macarons</option>
                        </select>
                    </div>
                    <div class="mb-4">
                        <label class="form-label small fw-medium text-brand">Product Image</label>
                        <input type="file" class="form-control form-control-sm" name="image" accept="image/*">
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

<div class="modal fade" id="editProductModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header border-bottom-0">
                <h5 class="modal-title fw-bold text-brand">Edit Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="editProduct" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="productId" value="1">
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Product Name</label>
                        <input type="text" class="form-control form-control-sm py-2" name="name" value="Strawberry Cupcake" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium text-brand">Description</label>
                        <textarea class="form-control form-control-sm py-2" name="description" rows="3" required>Delicious strawberry cupcake with pink frosting.</textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label small fw-medium">Price ($)</label>
                            <input type="number" step="0.01" class="form-control form-control-sm py-2" name="price" value="4.99" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label small fw-medium">Quantity</label>
                            <input type="number" class="form-control form-control-sm py-2" name="quantity" value="50" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Category</label>
                        <select class="form-select form-select-sm py-2" name="categoryId" required>
                            <option value="1" selected>Cupcakes</option>
                            <option value="2">Cakes</option>
                            <option value="3">Cookies</option>
                            <option value="4">Donuts</option>
                            <option value="5">Macarons</option>
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

<div class="modal fade" id="deleteProductModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-sm">
        <div class="modal-content text-center p-3">
            <div class="modal-body">
                <i class="bi bi-exclamation-circle text-danger fs-1 mb-3"></i>
                <h5 class="fw-bold mb-2">Delete Product?</h5>
                <p class="text-muted small mb-4">Are you sure you want to delete <strong id="deleteProductName" class="text-dark"></strong>? This action cannot be undone.</p>

                <form action="deleteProduct" method="POST">
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
</script>
</body>
</html>