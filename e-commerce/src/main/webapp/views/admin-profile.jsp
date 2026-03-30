<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Profile - Admin | Sweet Delights</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=DM+Serif+Display:ital@0;1&display=swap" rel="stylesheet">

  <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

  <link href="${pageContext.request.contextPath}/static/css/globals.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/static/css/admin/dashboard.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/static/css/profile/main.css" rel="stylesheet">
</head>
<body class="bg-white d-flex flex-column min-vh-100">

<!-- Include Admin Header -->
<jsp:include page="/static/html/admin-header.jsp" />


<div class="dash-header-bg py-5">
  <div class="container">
    <h1 class="font-serif fw-bold text-dark mb-1" style="font-size: 2.5rem; letter-spacing: -1px;">My Profile</h1>
    <p class="text-muted-custom mb-0" style="font-size: 0.95rem;">Manage your admin account details</p>
  </div>
</div>

<!-- Profile Content -->
<main class="flex-grow-1 py-5 bg-light">
  <div class="container" style="max-width: 900px;">

    <!-- Alerts -->
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

    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2 class="h4 fw-bold mb-0">Account Information</h2>
      <button class="btn btn-outline-dark rounded-3 px-4 py-2"
              data-bs-toggle="modal" data-bs-target="#editProfileModal">
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
              <p class="mb-0 fw-medium fs-5">
                <span class="badge bg-dark px-3 py-2 rounded-pill">${sessionScope.user.role}</span>
              </p>
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-5">
        <div class="card border-0 shadow-sm p-4 rounded-4 mb-4">
          <h3 class="h5 fw-bold mb-2"><i class="bi bi-wallet2 me-2"></i> Credit Balance</h3>
          <div class="display-6 fw-bold text-success">$${sessionScope.user.creditBalance}</div>
        </div>
        <div class="card border-0 shadow-sm p-4 rounded-4 text-center py-4">
          <i class="bi bi-shield-check fs-1 text-muted mb-2"></i>
          <p class="text-muted mb-0 small">Admin account — full dashboard access</p>
          <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-sm btn-outline-dark mt-3 rounded-3">
            <i class="bi bi-speedometer2 me-1"></i> Back to Dashboard
          </a>
        </div>
      </div>
    </div>
  </div>
</main>

<!-- Edit Profile Modal -->
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
        <form action="${pageContext.request.contextPath}/admin/profile" method="POST">
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
</body>
</html>
