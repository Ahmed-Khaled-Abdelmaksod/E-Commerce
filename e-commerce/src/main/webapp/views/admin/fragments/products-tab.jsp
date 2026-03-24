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
                        <img src="${pageContext.request.contextPath}${product.imageUrl}" class="rounded product-img-thumbnail" style="width: 50px; height: 50px; object-fit: cover;">
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
        </tbody>
    </table>
</div>

<div class="modal fade" id="addProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title fw-bold text-brand">Add New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/addProduct" method="POST" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Product Name</label>
                        <input type="text" class="form-control" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Description</label>
                        <textarea class="form-control" name="description" rows="2" required></textarea>
                    </div>
                    <div class="row mb-3">
                        <div class="col-6">
                            <label class="form-label small fw-medium">Price ($)</label>
                            <input type="number" step="0.01" class="form-control" name="price" required>
                        </div>
                        <div class="col-6">
                            <label class="form-label small fw-medium">Quantity</label>
                            <input type="number" class="form-control" name="quantity" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label small fw-medium">Category</label>
                        <div class="input-group">
                            <select class="form-select category-dropdown" name="categoryId" required>
                                <option value="" disabled selected>Select Category</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.categoryId}">${cat.name}</option>
                                </c:forEach>
                            </select>
                            <button class="btn btn-outline-brand" type="button" data-bs-toggle="modal" data-bs-target="#addCategoryModal">
                                <i class="bi bi-plus-lg"></i>
                            </button>
                        </div>
                    </div>
                    <div class="mb-4">
                        <label class="form-label small fw-medium">Product Image</label>
                        <input type="file" class="form-control" name="image" accept="image/*" required>
                    </div>
                    <div class="d-flex justify-content-end gap-2">
                        <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-brand">Save Product</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<c:forEach var="product" items="${products}">
    <div class="modal fade" id="editProductModal${product.productId}" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold text-brand">Edit ${product.name}</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/editProduct" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <div class="mb-3">
                            <label class="form-label small fw-medium">Product Name</label>
                            <input type="text" class="form-control" name="name" value="${product.name}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-medium">Description</label>
                            <textarea class="form-control" name="description" rows="2" required>${product.description}</textarea>
                        </div>
                        <div class="row mb-3">
                            <div class="col-6">
                                <label class="form-label small fw-medium">Price ($)</label>
                                <input type="number" step="0.01" class="form-control" name="price" value="${product.price}" required>
                            </div>
                            <div class="col-6">
                                <label class="form-label small fw-medium">Quantity</label>
                                <input type="number" class="form-control" name="quantity" value="${product.stockQuantity}" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small fw-medium">Category</label>
                            <select class="form-select category-dropdown" name="categoryId" required>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.categoryId}" ${cat.categoryId == product.categoryId ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-4">
                            <label class="form-label small fw-medium">Update Image (Optional)</label>
                            <input type="file" class="form-control" name="image" accept="image/*">
                        </div>
                        <div class="d-flex justify-content-end gap-2">
                            <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-brand">Update Product</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<div class="modal fade" id="addCategoryModal" tabindex="-1" style="z-index: 1070;">
    <div class="modal-dialog modal-sm modal-dialog-centered">
        <div class="modal-content shadow-lg">
            <div class="modal-header border-0">
                <h6 class="modal-title fw-bold">New Category</h6>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <input type="text" id="newCategoryName" class="form-control mb-2" placeholder="Name">
                <textarea id="newCategoryDesc" class="form-control form-control-sm" placeholder="Description"></textarea>
            </div>
            <div class="modal-footer border-0">
                <button type="button" class="btn btn-brand btn-sm w-100" onclick="ajaxAddCategory()">Save Category</button>
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
                <p class="text-muted small mb-4">Are you sure about <strong id="deleteProductName"></strong>?</p>
                <form id="deleteForm" onsubmit="executeDeleteAjax(event)">
                    <input type="hidden" id="deleteProductId">
                    <div class="d-flex justify-content-center gap-2">
                        <button type="button" class="btn btn-light w-50" data-bs-dismiss="modal">No</button>
                        <button type="submit" class="btn btn-danger w-50">Yes, Delete</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function confirmDelete(id, name) {
        document.getElementById('deleteProductName').innerText = name;
        document.getElementById('deleteProductId').value = id;
    }

    function executeDeleteAjax(event) {
        event.preventDefault();
        const id = document.getElementById('deleteProductId').value;
        const ctx = "${pageContext.request.contextPath}";

        fetch(ctx + '/deleteProduct', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'productId=' + encodeURIComponent(id)
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === 'success') {
                    location.reload();
                }
            });
    }
</script>