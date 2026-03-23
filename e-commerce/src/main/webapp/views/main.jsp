<%--
  Created by IntelliJ IDEA.
  User: mosal
  Date: 3/24/2026
  Time: 1:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>My Profile - Sweet Delights</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/globals.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile/main.css">
</head>
<body class="d-flex flex-column min-vh-100">

<%@include file="/static/html/outer-header.jsp"%>

<main class="flex-grow-1 py-5 bg-light">
    <div class="container max-w-7xl">
        <div class="profile-header d-flex justify-content-between align-items-center mb-4">
            <h1 class="h2 fw-bold font-serif mb-0">My Profile</h1>
            <button class="btn btn-outline-dark rounded-3 px-4 py-2">
                <i class="bi bi-pencil me-2"></i> Edit Profile
            </button>
        </div>

        <div class="row g-4">
            <div class="col-lg-7">
                <div class="card border-0 shadow-sm p-4 rounded-4">
                    <h3 class="h5 fw-bold mb-4">Personal Information</h3>
                    <div class="info-list gap-3 d-flex flex-column">
                        <div>
                            <span class="text-muted small text-uppercase">Full Name</span>
                            <p class="mb-0 fw-medium fs-5">${sessionScope.user.fullName}</p>
                        </div>
                        <div>
                            <span class="text-muted small text-uppercase">Email</span>
                            <p class="mb-0 fw-medium fs-5">${sessionScope.user.email}</p>
                        </div>
                        <div>
                            <span class="text-muted small text-uppercase">Birthday</span>
                            <p class="mb-0 fw-medium fs-5">${sessionScope.user.birthday}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-5">
                <div class="card border-0 shadow-sm p-4 rounded-4 mb-4">
                    <h3 class="h5 fw-bold mb-2"><i class="bi bi-wallet2 me-2"></i> Credit Limit</h3>
                    <div class="display-6 fw-bold text-success">$${sessionScope.user.creditBalance}</div>
                </div>
                <div class="card border-0 shadow-sm p-4 rounded-4 text-center py-5">
                    <p class="text-muted mb-0">No order history available.</p>
                </div>
            </div>
        </div>
    </div>
</main>

<%@include file="/static/html/footer.html"%>

<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</body>
</html>