<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - Sweet Delights</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/globals.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <style>
        /* تنسيقات مخصصة لصفحة النجاح لتطابق الصورة تماماً */

        .success-icon-wrapper {
            width: 75px;
            height: 75px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1.5rem auto;
            /* استخدام كلاس الخلفية الفاتحة المرفق في الـ CSS الخاص بك */
            background-color: rgba(228, 105, 125, 0.1);
        }

        .success-icon-wrapper i {
            color: var(--primary);
            font-size: 2.2rem;
        }

        .btn-primary-custom {
            background-color: var(--primary);
            color: var(--primary-foreground);
            border: none;
            transition: opacity 0.2s ease;
        }

        .btn-primary-custom:hover {
            opacity: 0.9;
            color: var(--primary-foreground);
        }

        .btn-outline-custom {
            background-color: transparent;
            color: var(--foreground);
            border: 1px solid var(--border);
            transition: background-color 0.2s ease;
        }

        .btn-outline-custom:hover {
            background-color: var(--muted);
            color: var(--foreground);
        }

        .subtitle-text {
            color: var(--muted-foreground);
            font-size: 1.05rem;
        }
    </style>
</head>
<body class="font-sans d-flex flex-column min-vh-100" style="background-color: var(--background);">

    <%@include file="/static/html/header.html"%>

    <main class="flex-grow-1 d-flex align-items-center justify-content-center py-5">
        <div class="container px-4 text-center">

            <div class="success-icon-wrapper">
                <i class="bi bi-check-circle"></i>
            </div>

            <h1 class="font-serif fw-bold mb-3" style="font-size: 2.5rem; color: var(--foreground); letter-spacing: -0.5px;">
                Order Confirmed!
            </h1>

            <p class="subtitle-text mb-1">
                Your order <span class="fw-medium">#order-5</span> has been placed successfully.
            </p>
            <p class="subtitle-text mb-4 pb-2" style="font-size: 0.95rem;">
                Thank you for choosing Sweet Delights!
            </p>

            <div class="d-flex flex-column gap-3 mx-auto" style="max-width: 400px;">

                <a href="${pageContext.request.contextPath}/user/products" class="btn btn-primary-custom py-2 rounded-3 fw-medium">
                    Continue Shopping
                </a>

                <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-outline-custom py-2 rounded-3 fw-medium">
                    View Order History
                </a>

            </div>

        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>