<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Sweet Delights - Browse Sweets</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>
    </head>
    <body>
        <%@ include file="/static/html/guest-header.html"%>

        <main class="flex-grow-1">
            <section class="bg-light py-5 border-bottom">
                <div class="container py-2">
                    <h1 class="display-6 fw-bold font-serif text-dark">Our Sweets</h1>
                    <p class="text-muted mb-0">Browse our full collection of handcrafted treats</p>
                </div>
            </section>

            <section class="py-5">
                <div class="container">
                    <div class="d-flex flex-column gap-4 flex-md-row align-items-md-center justify-content-between mb-5">
                        <div class="d-flex flex-wrap gap-2">
                            <button class="filter-btn btn btn-primary bg-brand border-0 btn-sm px-3 rounded-pill custom-raduis light-border-color border-light-subtle shadow-sm" data-filter="all">All</button>
                            <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                            <c:forEach var="category" items="${categories}">
                                <button class="filter-btn btn btn-outline-secondary btn-sm px-3 custom-raduis light-border-color text-dark border-light-subtle shadow-sm bg-white hover-brown" data-filter="${category.name}">${category.name}</button>
                            </c:forEach>
                        </div>

                        <div class="position-relative search-container" style="min-width: 260px;" >
                            <i class="bi bi-search position-absolute start-0 top-50 translate-middle-y ms-3 custom-search-icon"></i>
                            <input type="text"
                                   class="form-control ps-5 py-2 rounded-pill custom-search-input"
                                   placeholder="Search products..." id="searchInput">
                        </div>
                    </div>

                    <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4">
                        <jsp:useBean id="products" scope="request" type="java.util.List"/>
                        <c:forEach var="product" items="${products}">
                            <div class="col product-item" data-category="${product.categoryName}">
                                <div class="card h-100 border-0 shadow-sm rounded-4 product-card overflow-hidden transition-all">
                                    <div class="position-relative overflow-hidden aspect-square bg-light">
                                        <img src="${pageContext.request.contextPath}${product.imageUrl}"
                                             class="card-img-top object-cover h-100 w-100 transition-transform zoom-hover"
                                             alt="${product.categoryName}">
                                        <div class="position-absolute top-0 start-0 m-3">
                                            <span class="badge bg-white text-muted fw-medium py-2 px-3 rounded-pill shadow-sm small border">${product.categoryName}</span>
                                        </div>
                                    </div>

                                    <div class="card-body p-4">
                                        <h3 class="h6 fw-bold text-dark mb-2">${product.name}</h3>
                                        <div class="d-flex align-items-center justify-content-between mt-3">
                                            <span class="h5 fw-bold text-brand mb-0">$${product.price}</span>
                                            <button class="guest-add-to-cart-btn btn btn-primary bg-brand border-0 btn-sm px-3 d-flex align-items-center rounded-3"
                                                    data-id="${product.productId}"
                                                    data-name="${product.name}"
                                                    data-price="${product.price}"
                                                    data-image="${product.imageUrl}">
                                                <i class="bi bi-cart-plus me-2"></i> Add
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>
        </main>

        <%@ include file="/static/html/footer.html"%>

        <!-- Toast -->
        <div class="toast-container position-fixed bottom-0 end-0 p-3" style="z-index: 1090;">
            <div id="cartToast" class="toast align-items-center text-bg-success border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body" id="cartToastMessage">Item added to cart!</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </div>

        <script>
            const CONTEXT_PATH = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/guest-cart.js?v=3"></script>
    </body>
</html>
