<header class="navbar navbar-expand-md sticky-top bg-card-glass border-bottom px-lg-4">
  <div class="container-fluid max-w-7xl d-flex align-items-center">

    <a class="navbar-brand d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/admin/dashboard">
      <div class="brand-logo-circle">
        <span class="font-serif">S</span>
      </div>
      <span class="brand-text font-serif fw-bold h4 mb-0">Sweet Delights</span>
    </a>

    <div class="collapse navbar-collapse justify-content-center order-3 order-md-2" id="navbarNav">
      <ul class="navbar-nav gap-md-4 py-3 py-md-0 text-center text-md-start">
        <li class="nav-item">
          <a class="nav-link text-muted hover-dark font-medium small" href="${pageContext.request.contextPath}/products">Shop</a>
        </li>
        <li class="nav-item">
          <a class="nav-link text-muted hover-dark font-medium small" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        </li>
        <li class="nav-item d-md-none">
          <a class="nav-link text-muted hover-dark font-medium small" href="${pageContext.request.contextPath}/admin/profile">Profile</a>
        </li>
        <li class="nav-item d-md-none">
          <a class="nav-link text-muted hover-dark font-medium small" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </li>
      </ul>
    </div>

    <div class="d-flex align-items-center gap-2 ms-auto order-2 order-md-3">
      <a href="${pageContext.request.contextPath}/admin/profile" class="btn btn-icon">
        <i class="bi bi-person fs-5"></i>
        <span class="visually-hidden">Profile</span>
      </a>

      <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-icon d-none d-md-inline-flex">
        <i class="bi bi-box-arrow-right fs-5"></i>
        <span class="visually-hidden">Log out</span>
      </a>

      <button class="navbar-toggler btn-icon border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <i class="bi bi-list fs-4"></i>
      </button>
    </div>

  </div>
</header>