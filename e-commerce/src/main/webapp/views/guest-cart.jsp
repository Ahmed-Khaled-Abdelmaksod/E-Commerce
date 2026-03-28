<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sweet Delights - Your Cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body class="d-flex flex-column min-vh-100">
    <%@include file="/static/html/guest-header.html"%>

    <!-- Empty cart state -->
    <div id="guest-empty-cart" class="flex-grow-1 d-flex flex-column align-items-center justify-content-center text-center py-5">
        <i class="bi bi-cart-x" style="font-size: 4rem; color: var(--muted-foreground);"></i>
        <h2 class="h4 fw-bold mt-3">Your cart is empty</h2>
        <p class="text-muted mb-4">Looks like you haven't added any treats yet!</p>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary bg-brand border-0 rounded-3 px-4 py-2">
            Browse Sweets <i class="bi bi-arrow-right ms-1"></i>
        </a>
    </div>

    <!-- Filled cart state (hidden by default, shown by JS) -->
    <div id="guest-filled-cart" style="display: none;">
        <main class="flex-grow-1 py-5">
            <div class="container max-w-7xl">
                <h1 class="h2 fw-bold font-serif mb-4 px-2">Shopping Cart</h1>

                <div class="row g-4">
                    <div class="col-lg-8">
                        <div class="d-flex flex-column gap-3" id="guest-cart-items">
                            <!-- Rendered by guest-cart.js -->
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="card border rounded-3 p-4 shadow-sm sticky-top custom-sticky-offset">
                            <h2 class="h5 fw-semibold mb-4">Order Summary</h2>
                            <div id="guest-cart-summary">
                                <!-- Rendered by guest-cart.js -->
                            </div>
                            <hr class="my-3 border-secondary-subtle">

                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span class="fw-semibold">Total</span>
                                <span class="h5 fw-bold text-brand mb-0">$<span id="guest-grand-total">0.00</span></span>
                            </div>

                            <div class="alert alert-info small mb-3 d-flex align-items-start gap-2" role="alert">
                                <i class="bi bi-info-circle-fill mt-1"></i>
                                <span>Please sign in or create an account to complete your order.</span>
                            </div>

                            <a href="${pageContext.request.contextPath}/auth/login?checkout=true"
                               class="btn btn-primary bg-brand border-0 w-100 rounded-3 py-2 fw-medium d-flex align-items-center justify-content-center">
                                Sign In to Checkout
                                <i class="bi bi-arrow-right ms-2"></i>
                            </a>

                            <a href="${pageContext.request.contextPath}/auth/register?checkout=true"
                               class="btn btn-outline-secondary w-100 rounded-3 py-2 fw-medium mt-2 d-flex align-items-center justify-content-center">
                                Create Account
                                <i class="bi bi-person-plus ms-2"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <%@include file="/static/html/footer.html"%>

    <script>
        const CONTEXT_PATH = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/guest-cart.js?v=3"></script>
</body>
</html>
