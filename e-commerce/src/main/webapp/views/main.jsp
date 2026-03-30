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
            <button class="btn btn-outline-dark rounded-3 px-4 py-2"
                    data-bs-toggle="modal" data-bs-target="#editProfileModal">
                <i class="bi bi-pencil me-2"></i> Edit Profile
            </button>
        </div>

        <%-- Success / Error alerts --%>
        <c:if test="${param.updated == 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle me-2"></i> Profile updated successfully!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-circle me-2"></i> ${requestScope.errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

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
                            <span class="text-muted small text-uppercase">Phone</span>
                            <p class="mb-0 fw-medium fs-5">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user.phone}">${sessionScope.user.phone}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Not provided</span></c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div>
                            <span class="text-muted small text-uppercase">Birthday</span>
                            <p class="mb-0 fw-medium fs-5">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user.birthday}">${sessionScope.user.birthday}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Not provided</span></c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div>
                            <span class="text-muted small text-uppercase">Address</span>
                            <p class="mb-0 fw-medium fs-5">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user.address}">${sessionScope.user.address}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Not provided</span></c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div>
                            <span class="text-muted small text-uppercase">Role</span>
                            <p class="mb-0 fw-medium fs-5">${sessionScope.user.role}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-5">
                <div class="card border-0 shadow-sm p-4 rounded-4 mb-4">
                    <h3 class="h5 fw-bold mb-2"><i class="bi bi-wallet2 me-2"></i> Credit Limit</h3>
                    <div class="display-6 fw-bold text-success">$${sessionScope.user.creditBalance}</div>
                    <p class="text-muted small mb-0 mt-1">Available balance for purchases</p>
                </div>
                <div class="card border-0 shadow-sm p-3 rounded-4">
                    <h3 class="h5 fw-bold mb-3"><i class="bi bi-receipt me-2"></i>Order History</h3>
                    <c:choose>
                        <c:when test="${empty orders}">
                            <div class="text-center py-4">
                                <i class="bi bi-bag-x fs-1 text-muted"></i>
                                <p class="text-muted mt-2 mb-0 small">No orders placed yet.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="d-flex flex-column gap-2">
                                <c:forEach var="order" items="${orders}">
                                    <div class="d-flex justify-content-between align-items-center border rounded-3 px-3 py-2">
                                        <div>
                                            <div class="fw-semibold small">#${order.orderId}</div>
                                            <div class="text-muted" style="font-size:0.75rem;">${order.orderDate}</div>
                                        </div>
                                        <div class="fw-bold text-success small">$${order.totalPrice}</div>
                                        <c:choose>
                                            <c:when test="${order.status == 'COMPLETED'}">
                                                <span class="badge bg-success rounded-pill">${order.status}</span>
                                            </c:when>
                                            <c:when test="${order.status == 'PENDING'}">
                                                <span class="badge bg-warning text-dark rounded-pill">${order.status}</span>
                                            </c:when>
                                            <c:when test="${order.status == 'CANCELLED'}">
                                                <span class="badge bg-danger rounded-pill">${order.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-info rounded-pill">${order.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</main>

<%-- Edit Profile Modal --%>
<div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content rounded-4 border-0 shadow">
            <div class="modal-header border-0 pb-0">
                <h5 class="modal-title fw-bold" id="editProfileModalLabel">
                    <i class="bi bi-pencil-square me-2"></i>Edit Profile
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body pt-3">
                <form action="${pageContext.request.contextPath}/user/profile" method="POST">
                    <div class="mb-3">
                        <label for="fullName" class="form-label small fw-semibold text-muted text-uppercase">Full Name</label>
                        <input type="text" class="form-control rounded-3" id="fullName" name="fullName"
                               value="${sessionScope.user.fullName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label small fw-semibold text-muted text-uppercase">Phone</label>
                        <input type="tel" class="form-control rounded-3" id="phone" name="phone"
                               value="${sessionScope.user.phone}">
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label small fw-semibold text-muted text-uppercase">Address</label>
                        <input type="text" class="form-control rounded-3" id="address" name="address"
                               value="${sessionScope.user.address}">
                    </div>
                    <div class="mb-4">
                        <label for="birthday" class="form-label small fw-semibold text-muted text-uppercase">Birthday</label>
                        <input type="date" class="form-control rounded-3" id="birthday" name="birthday"
                               value="${sessionScope.user.birthday}">
                    </div>
                    <div class="d-flex justify-content-end gap-2">
                        <button type="button" class="btn btn-light rounded-3 px-4" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-dark rounded-3 px-4">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="/static/html/footer.html"%>

<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</body>
</html>