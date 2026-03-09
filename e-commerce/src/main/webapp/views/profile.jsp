<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sweet Delights - My Profile</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/globals.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile/main.css">
</head>
<body>
    <%@include file="/static/html/header.html"%>

    <%@include file="/static/html/profile/main.html"%>

    <%@include file="/static/html/footer.html"%>

    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</body>
</html>