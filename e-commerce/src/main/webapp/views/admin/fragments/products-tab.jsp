<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="container-fluid px-0">

    <%-- ── Header: Title + Add Product ── --%>
    <div class="d-flex justify-content-between align-items-center mb-4 mt-2">
        <h4 class="font-serif fw-bold mb-0" style="font-size: 1.35rem; letter-spacing: -0.4px; color: var(--foreground);">
            Products (${products.size()})
        </h4>
        <button class="btn-add-product" onclick="toggleProductForm()">
            <i class="bi bi-plus-lg"></i>
            Add Product
        </button>
    </div>

    <%-- ── Inline Add / Edit Form ── --%>
    <div id="productFormContainer" class="mb-5 d-none">
        <div style="padding: 28px 32px;">

            <%-- Form header --%>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h5 id="formTitle" class="fw-bold mb-0" style="font-size: 1rem; color: var(--foreground);">
                    New Product
                </h5>
                <button type="button" class="btn-close-form" onclick="toggleProductForm()" aria-label="Close">
                    <i class="bi bi-x-lg" style="font-size: 1rem;"></i>
                </button>
            </div>

            <form id="productActionForm"
                  action="${pageContext.request.contextPath}/addProduct"
                  method="POST"
                  enctype="multipart/form-data">
                <input type="hidden" name="productId" id="formProductId">
                <%-- ✅ FIX: حفظ الصورة الحالية عشان لو المستخدم معدلش الصورة منحدفهاش --%>
                <input type="hidden" name="currentImageUrl" id="formCurrentImageUrl">

                <div class="row g-3">
                    <%-- Name --%>
                    <div class="col-md-6">
                        <label class="form-label" for="formName">Name</label>
                        <input type="text" class="form-control" name="name" id="formName" required>
                    </div>

                    <%-- Category (dropdown from DB) --%>
                    <div class="col-md-6">
                        <label class="form-label" for="formCategorySelect">Category</label>
                        <select class="form-select category-dropdown" name="categoryId" id="formCategorySelect" required>
                            <option value="" disabled selected>e.g., Cupcakes, Cakes</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}">${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- Price --%>
                    <div class="col-md-6">
                        <label class="form-label" for="formPrice">Price ($)</label>
                        <input type="number" step="0.01" class="form-control"
                               name="price" id="formPrice" required>
                    </div>

                    <%-- Quantity --%>
                    <div class="col-md-6">
                        <label class="form-label" for="formQuantity">Quantity</label>
                        <input type="number" class="form-control"
                               name="quantity" id="formQuantity" required>
                    </div>

                    <%-- Image: URL + Upload --%>
                    <div class="col-12">
                        <label class="form-label">Image</label>

                        <%-- Toggle tabs --%>
                        <div class="img-input-tabs mb-2">
                            <button type="button" class="img-tab active" id="tabUrl"
                                    onclick="switchImgTab('url')">
                                <i class="bi bi-link-45deg"></i> URL
                            </button>
                            <button type="button" class="img-tab" id="tabUpload"
                                    onclick="switchImgTab('upload')">
                                <i class="bi bi-upload"></i> Upload
                            </button>
                        </div>

                        <%-- URL panel --%>
                        <div id="imgPanelUrl">
                            <input type="text" class="form-control" name="imageUrl" id="formImageUrl"
                                   placeholder="/images/cupcake.jpg"
                                   oninput="previewFromUrl(this.value)">
                        </div>

                        <%-- Upload panel --%>
                        <div id="imgPanelUpload" class="d-none">
                            <div class="img-upload-zone" id="imgDropZone"
                                 onclick="document.getElementById('formImageFile').click()"
                                 ondragover="event.preventDefault(); this.classList.add('dragging')"
                                 ondragleave="this.classList.remove('dragging')"
                                 ondrop="handleImgDrop(event)">
                                <i class="bi bi-cloud-arrow-up" style="font-size:1.6rem; color: var(--muted-foreground);"></i>
                                <p class="mb-0 mt-1" style="font-size:0.85rem; color: var(--muted-foreground);">
                                    Click or drag &amp; drop an image
                                </p>
                                <p class="mb-0" style="font-size:0.78rem; color: var(--muted-foreground); opacity:0.7;">
                                    PNG, JPG, WEBP — max 5 MB
                                </p>
                            </div>
                            <input type="file" class="d-none" name="image" id="formImageFile"
                                   accept="image/*" onchange="previewFromFile(this)">
                        </div>

                        <%-- Preview --%>
                        <div id="imgPreviewWrap" class="d-none mt-2">
                            <div class="d-flex align-items-center gap-3">
                                <img id="imgPreview" src="" alt="Preview"
                                     style="width:60px; height:60px; border-radius:8px; object-fit:cover; border:1px solid var(--border);">
                                <div>
                                    <p id="imgPreviewName" class="mb-0" style="font-size:0.83rem; color:var(--foreground); font-weight:500;"></p>
                                    <button type="button" class="img-clear-btn" onclick="clearImgPreview()">
                                        <i class="bi bi-x-circle"></i> Remove
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%-- Description --%>
                    <div class="col-12">
                        <label class="form-label" for="formDescription">Description</label>
                        <textarea class="form-control" name="description"
                                  id="formDescription" rows="3"></textarea>
                    </div>

                    <%-- Highlighted --%>
                    <div class="col-12">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox"
                                   name="highlighted" id="formHighlighted">
                            <label class="form-check-label" for="formHighlighted"
                                   style="font-size: 0.88rem; color: var(--foreground);">
                                Highlight this product on customer dashboard
                            </label>
                        </div>
                    </div>
                </div>

                <%-- Submit --%>
                <div class="d-flex justify-content-end mt-4">
                    <button type="submit" class="btn-submit-form">
                        <i class="bi bi-floppy" style="font-size: 0.88rem;"></i>
                        <span id="submitBtnText">Add Product</span>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <%-- ── Products Table ── --%>
    <table class="table products-table">
        <thead>
            <tr>
                <th>Product</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
                <th class="text-end">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${products}">
                <tr id="product-row-${p.productId}">

                    <%-- Product: image + name --%>
                    <td>
                        <div class="d-flex align-items-center gap-3">
                            <img src="${pageContext.request.contextPath}${p.imageUrl}"
                                 alt="${p.name}"
                                 class="product-thumb">
                            <span class="product-name">${p.name}</span>
                        </div>
                    </td>

                    <%-- Category --%>
                    <td class="cell-muted">${p.categoryName}</td>

                    <%-- Price --%>
                    <td class="cell-price">$${p.price}</td>

                    <%-- Stock badge --%>
                    <td>
                        <span class="stock-badge">${p.stockQuantity}</span>
                    </td>

                    <%-- Actions --%>
                    <td class="text-end">
                        <button class="action-btn"
                                title="Edit"
                                data-id="${p.productId}"
                                data-name="${p.name}"
                                data-desc="${p.description}"
                                data-price="${p.price}"
                                data-stock="${p.stockQuantity}"
                                data-catid="${p.categoryId}"
                                data-catname="${p.categoryName}"
                                data-imgurl="${p.imageUrl}"
                                data-high="${p.highlighted}"
                                onclick="handleEditClick(this)">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="action-btn action-btn-delete"
                                title="Delete"
                                onclick="confirmDelete(${p.productId}, '${p.name}')"
                                data-bs-toggle="modal"
                                data-bs-target="#deleteProductModal">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

<script>
    function toggleProductForm() {
        const container = document.getElementById('productFormContainer');
        if (!container) return;
        if (container.classList.contains('d-none')) {
            _resetForm();
            container.classList.remove('d-none');
            window.scrollTo({ top: 0, behavior: 'smooth' });
        } else {
            container.classList.add('d-none');
        }
    }

    function _resetForm() {
        const form = document.getElementById('productActionForm');
        if (form) {
            form.reset();
            form.action = "${pageContext.request.contextPath}/addProduct";
        }
        document.getElementById('formTitle').innerText       = "New Product";
        document.getElementById('submitBtnText').innerText   = "Add Product";
        document.getElementById('formProductId').value       = "";
        document.getElementById('formCurrentImageUrl').value = "";
        clearImgPreview();
    }

    /* ── Image input: URL ↔ Upload toggle ── */

    function switchImgTab(tab) {
        const isUrl = tab === 'url';
        document.getElementById('tabUrl').classList.toggle('active', isUrl);
        document.getElementById('tabUpload').classList.toggle('active', !isUrl);
        document.getElementById('imgPanelUrl').classList.toggle('d-none', !isUrl);
        document.getElementById('imgPanelUpload').classList.toggle('d-none', isUrl);
        clearImgPreview();
    }

    function previewFromUrl(val) {
        if (!val.trim()) { clearImgPreview(); return; }
        const wrap = document.getElementById('imgPreviewWrap');
        const img  = document.getElementById('imgPreview');
        const name = document.getElementById('imgPreviewName');
        img.src  = val;
        img.onerror = () => clearImgPreview();
        name.textContent = val.split('/').pop();
        wrap.classList.remove('d-none');
    }

    function previewFromFile(input) {
        if (!input.files || !input.files[0]) return;
        const file = input.files[0];
        const url  = URL.createObjectURL(file);
        const wrap = document.getElementById('imgPreviewWrap');
        const img  = document.getElementById('imgPreview');
        const name = document.getElementById('imgPreviewName');
        img.src  = url;
        name.textContent = file.name;
        wrap.classList.remove('d-none');
        document.getElementById('imgDropZone').classList.add('has-file');
    }

    function handleImgDrop(event) {
        event.preventDefault();
        document.getElementById('imgDropZone').classList.remove('dragging');
        const file = event.dataTransfer.files[0];
        if (!file || !file.type.startsWith('image/')) return;
        const dt = new DataTransfer();
        dt.items.add(file);
        const input = document.getElementById('formImageFile');
        input.files = dt.files;
        previewFromFile(input);
    }

    function clearImgPreview() {
        document.getElementById('imgPreviewWrap').classList.add('d-none');
        document.getElementById('imgPreview').src = '';
        document.getElementById('imgPreviewName').textContent = '';
        const fi = document.getElementById('formImageFile');
        if (fi) fi.value = '';
        const zone = document.getElementById('imgDropZone');
        if (zone) zone.classList.remove('has-file', 'dragging');
    }

    function handleEditClick(btn) {
        const container = document.getElementById('productFormContainer');
        if (container) container.classList.remove('d-none');
        window.scrollTo({ top: 0, behavior: 'smooth' });

        document.getElementById('formTitle').innerText     = "Edit Product";
        document.getElementById('submitBtnText').innerText = "Update Product";

        const form = document.getElementById('productActionForm');
        form.action = "${pageContext.request.contextPath}/editProduct";

        const currentImgUrl = btn.getAttribute('data-imgurl') || '';

        document.getElementById('formProductId').value       = btn.getAttribute('data-id');
        document.getElementById('formName').value            = btn.getAttribute('data-name');
        document.getElementById('formDescription').value     = btn.getAttribute('data-desc');
        document.getElementById('formPrice').value           = btn.getAttribute('data-price');
        document.getElementById('formQuantity').value        = btn.getAttribute('data-stock');
        document.getElementById('formImageUrl').value        = currentImgUrl;
        document.getElementById('formCurrentImageUrl').value = currentImgUrl;

        previewFromUrl(currentImgUrl);

        const sel = document.getElementById('formCategorySelect');
        if (sel) sel.value = btn.getAttribute('data-catid') || '';

        document.getElementById('formHighlighted').checked =
            (btn.getAttribute('data-high') === 'true');
    }
</script>
