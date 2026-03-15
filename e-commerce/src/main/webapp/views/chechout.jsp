<%--
  Created by IntelliJ IDEA.
  User: ak490
  Date: 3/7/2026
  Time: 2:11 AM
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
    <%--TODO adjust all static data and make sure that all links are coreck --%>
    <main class="flex-grow-1 py-5 bg-light-subtle">
        <div class="container" style="max-width: 1000px;">
            <a href="${pageContext.request.contextPath}/cart" class="text-decoration-none text-muted small d-inline-flex align-items-center mb-3">
                <i class="bi bi-arrow-left me-1"></i> Back to Cart
            </a>

            <h1 class="h2 fw-bold font-serif mb-4 text-dark">Checkout</h1>

            <div class="row g-4">
                <div class="col-lg-8">
                    <div class="d-flex flex-column gap-4">

                        <div class="card border-0 shadow-sm rounded-4 p-4">
                            <h2 class="h5 fw-bold mb-4">Delivery Details</h2>
                            <div class="d-flex flex-column gap-2 small">
                                <div class="d-flex justify-content-between">
                                    <span class="text-muted">Name</span>
                                    <span class="fw-medium">Demo User</span>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <span class="text-muted">Email</span>
                                    <span class="fw-medium">demo@sweetdelights.com</span>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <span class="text-muted">Phone</span>
                                    <span class="fw-medium">+1 (555) 123-4567</span>
                                </div>
                                <div class="d-flex justify-content-between text-end">
                                    <span class="text-muted text-start">Address</span>
                                    <span class="fw-medium" style="max-width: 200px;">123 Sweet Street, Candy City</span>
                                </div>
                            </div>
                        </div>

                        <div class="card border-0 shadow-sm rounded-4 p-4">
                            <h2 class="h5 fw-bold mb-4">Order Items</h2>
                            <div class="d-flex flex-column gap-3">
                                <div class="d-flex align-items-center">
                                    <img src="${pageContext.request.contextPath}/static/images/macaron.jpg" alt="Macarons" class="rounded-3 me-3" style="width: 50px; height: 50px; object-fit: cover;">
                                    <div class="flex-grow-1">
                                        <p class="mb-0 fw-medium small">French Macarons</p>
                                        <p class="mb-0 text-muted extra-small">Qty: 2</p>
                                    </div>
                                    <span class="fw-bold small">$25.98</span>
                                </div>

                                <div class="d-flex align-items-center">
                                    <img src="${pageContext.request.contextPath}/static/images/chocolate-cake.jpg" alt="Cake" class="rounded-3 me-3" style="width: 50px; height: 50px; object-fit: cover;">
                                    <div class="flex-grow-1">
                                        <p class="mb-0 fw-medium small">Chocolate Cake</p>
                                        <p class="mb-0 text-muted extra-small">Qty: 5</p>
                                    </div>
                                    <span class="fw-bold small">$124.95</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card border-0 shadow-sm rounded-4 p-4 sticky-top" style="top: 100px; z-index: 10;">
                        <h2 class="h5 fw-bold mb-4">Payment Summary</h2>

                        <div class="d-flex justify-content-between small text-muted mb-2">
                            <span>Subtotal</span>
                            <span class="text-dark">$150.93</span>
                        </div>
                        <div class="d-flex justify-content-between small text-muted mb-3">
                            <span>Delivery</span>
                            <span class="text-dark">Free</span>
                        </div>

                        <hr class="my-3 text-secondary-emphasis opacity-25">

                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <span class="fw-bold">Total</span>
                            <span class="h4 fw-bold text-brand mb-0">$150.93</span>
                        </div>
                        <div class="d-flex justify-content-between extra-small text-muted mb-4">
                            <span>Your credit</span>
                            <span>$500.00</span>
                        </div>

                        <button class="btn btn-primary bg-brand border-0 w-100 rounded-3 py-2 fw-medium d-flex align-items-center justify-content-center">
                            <i class="bi bi-credit-card me-2"></i> Pay $150.93
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
</body>
</html>
