<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="featured-section">
    <div class="container">
        <div class="featured-header">
            <div>
                <span class="section-subtitle">Our Bestsellers</span>
                <h2 class="fw-bold section-title mb-0" style="margin-bottom: 0;">Popular Treats</h2>
            </div>
            <a href="${pageContext.request.contextPath}/user/products" class="view-all-link d-none d-md-flex align-items-center">
                View all <i class="bi bi-arrow-right ms-2"></i>
            </a>
        </div>

        <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-4 g-4">
            <jsp:useBean id="featuredProducts" scope="request" type="java.util.List"/>
            <c:forEach var="product" items="${featuredProducts}">
                <div class="col">
                    <div class="product-card">
                        <div class="product-img-wrapper">
                            <span class="product-badge">${product.categoryName}</span>
                            <a href="${pageContext.request.contextPath}/product/details?productId=${product.productId}" style="text-decoration: none; color: inherit;">
                                <img src="${pageContext.request.contextPath}${product.imageUrl}" alt="${product.name}" class="product-img">
                            </a>
                        </div>
                        <div class="product-info">
                            <h3 class="product-title"><a href="${pageContext.request.contextPath}/product/details?productId=${product.productId}" style="text-decoration: none; color: inherit;">${product.name}</a></h3>
                            <div class="product-footer">
                                <p class="product-price">$${product.price}</p>
                                <button class="btn-add add-to-cart-btn"
                                        data-id="${product.productId}"
                                        data-name="${product.name}"
                                        data-price="${product.price}"
                                        data-image="${product.imageUrl}"
                                        data-stock="${product.stockQuantity}"
                                        <c:if test="${product.stockQuantity <= 0}">disabled</c:if>
                                        style="<c:if test='${product.stockQuantity <= 0}'>opacity: 0.5; cursor: not-allowed;</c:if>">
                                    <i class="bi bi-cart3"></i> <c:choose><c:when test="${product.stockQuantity <= 0}">Out of Stock</c:when><c:otherwise>Add</c:otherwise></c:choose>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>

        <div class="mt-4 text-center d-md-none">
            <a href="${pageContext.request.contextPath}/user/products" class="view-all-link">
                View all <i class="bi bi-arrow-right ms-2"></i>
            </a>
        </div>
    </div>
</section>