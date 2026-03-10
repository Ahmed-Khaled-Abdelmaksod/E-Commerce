<%--
  Created by IntelliJ IDEA.
  User: ak490
  Date: 3/7/2026
  Time: 3:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css"/>

    </head>
    <body>
        <%@ include file="/static/html/header.html"%>
<%--        TODO adjust the view content with data from data base and adjust links--%>
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
                            <button class="btn btn-primary bg-brand border-0 btn-sm px-3 rounded-pill">All</button>
                            <button class="btn btn-outline-secondary btn-sm px-3 custom-raduis light-border-color text-dark border-light-subtle shadow-sm bg-white hover-brown">Cupcakes</button>
                            <button class="btn btn-outline-secondary btn-sm px-3 custom-raduis light-border-color text-dark border-light-subtle shadow-sm bg-white hover-brown">Macarons</button>
                            <button class="btn btn-outline-secondary btn-sm px-3 custom-raduis light-border-color text-dark border-light-subtle shadow-sm bg-white hover-brown">Cakes</button>
                            <button class="btn btn-outline-secondary btn-sm px-3 custom-raduis light-border-color text-dark border-light-subtle shadow-sm bg-white hover-brown">Cookies</button>
                            <button class="btn btn-outline-secondary btn-sm px-3 custom-raduis light-border-color text-dark border-light-subtle shadow-sm bg-white hover-brown">Donuts</button>
                        </div>

                        <<div class="position-relative search-container" style="min-width: 260px;">
                            <i class="bi bi-search position-absolute start-0 top-50 translate-middle-y ms-3 custom-search-icon"></i>

                            <input type="text"
                                   class="form-control ps-5 py-2 rounded-pill custom-search-input"
                                   placeholder="Search products...">
                        </div>
                    </div>

                    <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4">

                        <div class="col">
                            <div class="card h-100 border-0 shadow-sm rounded-4 product-card overflow-hidden transition-all">
                                <div class="position-relative overflow-hidden aspect-square bg-light">
                                    <img src="${pageContext.request.contextPath}/static/images/chocolate-cake.jpg"
                                         class="card-img-top object-cover h-100 w-100 transition-transform zoom-hover"
                                         alt="Strawberry Cupcake">
                                    <div class="position-absolute top-0 start-0 m-3">
                                        <span class="badge bg-white text-muted fw-medium py-2 px-3 rounded-pill shadow-sm small border">Cupcakes</span>
                                    </div>
                                </div>

                                <div class="card-body p-4">
                                    <h3 class="h6 fw-bold text-dark mb-2">Strawberry Cupcake</h3>
                                    <div class="d-flex align-items-center justify-content-between mt-3">
                                        <span class="h5 fw-bold text-brand mb-0">$4.99</span>
                                        <button class="btn btn-primary bg-brand border-0 btn-sm px-3 d-flex align-items-center rounded-3">
                                            <i class="bi bi-cart-plus me-2"></i> Add
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col">
                            <div class="card h-100 border-0 shadow-sm rounded-4 product-card overflow-hidden">
                                <div class="position-relative overflow-hidden aspect-square bg-light">
                                    <img src="${pageContext.request.contextPath}/static/images/chocolate-cake.jpg" class="card-img-top object-cover h-100 w-100 zoom-hover" alt="Macarons">
                                    <div class="position-absolute top-0 start-0 m-3">
                                        <span class="badge bg-white text-muted fw-medium py-2 px-3 rounded-pill shadow-sm small border">Macarons</span>
                                    </div>
                                </div>
                                <div class="card-body p-4">
                                    <h3 class="h6 fw-bold text-dark mb-2">French Macarons</h3>
                                    <div class="d-flex align-items-center justify-content-between mt-3">
                                        <span class="h5 fw-bold text-brand mb-0">$12.99</span>
                                        <button class="btn btn-primary bg-brand border-0 btn-sm px-3 d-flex align-items-center rounded-3">
                                            <i class="bi bi-cart-plus me-2"></i> Add
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </main>
        <%@ include file="/static/html/footer.html"%>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
    </body>
</html>
