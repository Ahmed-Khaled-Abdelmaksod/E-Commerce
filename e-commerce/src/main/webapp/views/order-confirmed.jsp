<%--
  Created by IntelliJ IDEA.
  User: ak490
  Date: 3/7/2026
  Time: 2:41 AM
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
<%--        TODO feed with order data --%>
        <main class="d-flex align-items-center justify-content-center min-vh-100 py-5">
            <div class="text-center" style="max-width: 450px; width: 100%; padding: 0 20px;">

                <div class="mx-auto mb-4 d-flex align-items-center justify-content-center rounded-circle"
                     style="width: 80px; height: 80px; background-color: rgba(218, 99, 122, 0.1);">
                    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none"
                         stroke="#da637a" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="lucide lucide-circle-check">
                        <circle cx="12" cy="12" r="10"></circle>
                        <path d="m9 12 2 2 4-4"></path>
                    </svg>
                </div>
    
                <h1 class="h2 fw-bold font-serif mb-3">Order Confirmed!</h1>

                <p class="text-muted mb-1">Your order #order-2 has been placed successfully.</p>
                <p class="text-muted small mb-5">Thank you for choosing Sweet Delights!</p>

                <div class="d-grid gap-3">
                    <a href="${pageContext.request.contextPath}/shop" class="btn btn-primary bg-brand border-0 rounded-3 py-2 fw-medium">
                        Continue Shopping
                    </a>
                    <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline-secondary hover-brown border rounded-3 py-2 fw-medium text-dark bg-white shadow-sm">
                        View Order History
                    </a>
                </div>
            </div>
        </main>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
    </body>
</html>
