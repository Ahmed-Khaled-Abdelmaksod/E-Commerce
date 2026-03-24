<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="container-fluid px-0">
    <div class="d-flex justify-content-between align-items-center mb-4 mt-3">
        <h4 class="font-serif fw-bold mb-0" style="color: var(--foreground); font-size: 1.5rem;">
            Products (${products.size()})
        </h4>
        <button class="btn btn-primary d-flex align-items-center gap-2 px-4 py-2" 
                style="background-color: #df6c7f; border: none; border-radius: var(--radius); font-weight: 500;"
                onclick="toggleProductForm()">
            <i class="bi bi-plus-lg"></i> Add Product
        </button>
    </div>

    <div id="productFormContainer" class="card border-0 shadow-sm mb-4 d-none" 
         style="border-radius: var(--radius); background-color: var(--card);">
        <div class="card-body p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h5 id="formTitle" class="fw-bold mb-0">New Product</h5>
                <button type="button" class="btn-close" onclick="toggleProductForm()"></button>
            </div>
            
            <form id="productActionForm" action="${pageContext.request.contextPath}/addProduct" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="productId" id="formProductId">
                
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label small fw-medium text-muted-custom">Name</label>
                        <input type="text" class="form-control bg-light border-0 py-2" name="name" id="formName" required 
                               style="border-radius: 0.5rem;">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label small fw-medium text-muted-custom">Category</label>
                        <select class="form-select bg-light border-0 py-2 category-dropdown" name="categoryId" id="formCategory" required
                                style="border-radius: 0.5rem;">
                            <option value="" disabled selected>e.g., Cupcakes, Cakes</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}">${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label small fw-medium text-muted-custom">Price ($)</label>
                        <input type="number" step="0.01" class="form-control bg-light border-0 py-2" name="price" id="formPrice" required
                               style="border-radius: 0.5rem;">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label small fw-medium text-muted-custom">Quantity</label>
                        <input type="number" class="form-control bg-light border-0 py-2" name="quantity" id="formQuantity" required
                               style="border-radius: 0.5rem;">
                    </div>
                    <div class="col-12">
                        <label class="form-label small fw-medium text-muted-custom">Image URL</label>
                        <input type="text" class="form-control bg-light border-0 py-2" placeholder="/static/images/cupcake.jpg" readonly
                               style="border-radius: 0.5rem; color: #a1a1a1;">
                        <input type="file" class="form-control mt-2" name="image" accept="image/*">
                    </div>
                    <div class="col-12">
                        <label class="form-label small fw-medium text-muted-custom">Description</label>
                        <textarea class="form-control bg-light border-0 py-2" name="description" id="formDescription" rows="3" required
                                  style="border-radius: 0.5rem;"></textarea>
                    </div>
                    <div class="col-12 mt-3">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="highlighted" id="formHighlighted">
                            <label class="form-check-label small text-muted-custom" for="formHighlighted">
                                Highlight this product on customer dashboard
                            </label>
                        </div>
                    </div>
                </div>
                
                <div class="d-flex justify-content-end mt-4">
                    <button type="submit" class="btn btn-primary px-4 py-2 d-flex align-items-center gap-2" 
                            style="background-color: #df6c7f; border: none; border-radius: 0.5rem;">
                        <i class="bi bi-save"></i> <span id="submitBtnText">Add Product</span>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="table-responsive">
        <table class="table table-hover align-middle border-0" style="--bs-table-hover-bg: #fdfaf7;">
            <thead class="text-muted-custom small border-bottom">
                <tr style="border-bottom: 1px solid var(--border);">
                    <th class="py-3 border-0 fw-medium">Product</th>
                    <th class="py-3 border-0 fw-medium">Category</th>
                    <th class="py-3 border-0 fw-medium">Price</th>
                    <th class="py-3 border-0 fw-medium">Stock</th>
                    <th class="py-3 border-0 fw-medium text-end">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr id="product-row-${product.productId}" class="border-bottom">
                        <td class="py-3 border-0">
                            <div class="d-flex align-items-center gap-3">
                                <img src="${pageContext.request.contextPath}${product.imageUrl}" 
                                     class="rounded" style="width: 42px; height: 42px; object-fit: cover;">
                                <span class="fw-medium" style="color: var(--foreground);">${product.name}</span>
                            </div>
                        </td>
                        <td class="py-3 border-0 text-muted-custom">${product.categoryName}</td>
                        <td class="py-3 border-0 fw-bold" style="color: var(--foreground);">$${product.price}</td>
                        <td class="py-3 border-0">
                            <span class="badge rounded-pill px-2 py-1" 
                                  style="background-color: #fce7eb; color: #df6c7f; font-weight: 500;">
                                ${product.stockQuantity}
                            </span>
                        </td>
                        <td class="py-3 border-0 text-end">
                            <button class="btn btn-link text-muted-custom p-1 me-2" 
                                    onclick="editProductInline(${product.productId}, '${product.name}', \`${product.description}\`, ${product.price}, ${product.stockQuantity}, ${product.categoryId}, ${product.highlighted})">
                                <i class="bi bi-pencil"></i>
                            </button>
                            <button class="btn btn-link text-muted-custom p-1" 
                                    onclick="confirmDelete('${product.productId}', '${product.name}')" 
                                    data-bs-toggle="modal" data-bs-target="#deleteProductModal">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    /**
     * إظهار/إخفاء نموذج إضافة المنتج المدمج
     */
    function toggleProductForm() {
        const container = document.getElementById('productFormContainer');
        container.classList.toggle('d-none');
        if (container.classList.contains('d-none')) {
            resetForm();
        }
    }

    /**
     * إعادة ضبط النموذج للحالة الافتراضية (Add Mode)
     */
    function resetForm() {
        document.getElementById('productActionForm').reset();
        document.getElementById('productActionForm').action = "${pageContext.request.contextPath}/addProduct";
        document.getElementById('formTitle').innerText = "New Product";
        document.getElementById('submitBtnText').innerText = "Add Product";
        document.getElementById('formProductId').value = "";
    }

    /**
     * ملء النموذج ببيانات المنتج الحالي لتحويله لوضع التعديل (Edit Mode)
     */
    function editProductInline(id, name, desc, price, stock, catId, highlighted) {
        const container = document.getElementById('productFormContainer');
        container.classList.remove('d-none');
        window.scrollTo({ top: 0, behavior: 'smooth' });

        document.getElementById('formTitle').innerText = "Edit Product";
        document.getElementById('submitBtnText').innerText = "Update Product";
        document.getElementById('productActionForm').action = "${pageContext.request.contextPath}/editProduct";
        
        document.getElementById('formProductId').value = id;
        document.getElementById('formName').value = name;
        document.getElementById('formDescription').value = desc;
        document.getElementById('formPrice').value = price;
        document.getElementById('formQuantity').value = stock;
        document.getElementById('formCategory').value = catId;
        document.getElementById('formHighlighted').checked = highlighted;
    }
</script>