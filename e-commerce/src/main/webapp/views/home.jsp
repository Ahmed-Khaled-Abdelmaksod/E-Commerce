<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sweet Delights - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/globals.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home/hero.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home/featured-products.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home/why-us.css">

</head>
<body class="d-flex flex-column min-vh-100">

    <%@include file="/static/html/guest-header.html"%>

    <main class="flex-grow-1">
        <%@include file="/static/html/home/hero.html"%>
        <%@include file="/views/featured-products-guest.jsp"%>
        <%@include file="/static/html/home/why-us.html"%>
    </main>

    <%@include file="/static/html/footer.html"%>

    <!-- Toast for cart notifications -->
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