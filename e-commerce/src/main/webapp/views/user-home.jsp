<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sweet Delights - Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/globals.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home/categories.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home/featured-products.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home/why-us.css">


</head>
<body class="d-flex flex-column min-vh-100">

<%@include file="/static/html/header.html"%>

<main class="flex-grow-1">

    <%@include file="/views/featured-products.jsp"%>
    <%@include file="/static/html/home/categories.html"%>
    <%@include file="/static/html/home/why-us.html"%>
</main>

<%@include file="/static/html/footer.html"%>

<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</body>
</html>