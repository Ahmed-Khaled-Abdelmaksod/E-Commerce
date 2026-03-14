<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Jakarta EE 10 / Tomcat 11 --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h4 class="fw-bold mb-0">Products (${products.size()})</h4>
    <button class="btn btn-primary-custom d-flex align-items-center gap-2">
        <i class="bi bi-plus-lg"></i> Add Product
    </button>
</div>

<div class="table-responsive">
    <table class="table table-borderless table-custom">
        <thead>
            <tr>
                <th style="width: 40%">Product</th>
                <th style="width: 20%">Category</th>
                <th style="width: 15%">Price</th>
                <th style="width: 15%">Stock</th>
                <th style="width: 10%" class="text-end">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <img src="${pageContext.request.contextPath}${not empty product.imageUrl ? product.imageUrl : '/static/images/default-cake.png'}" alt="Product" class="product-img">
                            <span class="fw-medium">${product.name}</span>
                        </div>
                    </td>
                    <td class="text-muted-custom">${product.categoryName}</td>
                    <td class="fw-bold">$${product.price}</td>
                    <td>
                        <span class="badge bg-primary-soft border-0 rounded-pill px-3 py-2 fw-medium">
                            ${product.stockQuantity}
                        </span>
                    </td>
                    <td class="text-end">
                        <i class="bi bi-pencil action-icon"></i>
                        <i class="bi bi-trash action-icon"></i>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>