<%@ page import="gov.iti.jets.ecommerce.service.CartService" %>
<%@ page import="gov.iti.jets.ecommerce.DTO.CartItemDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int cartCount = 0;
    jakarta.servlet.http.HttpSession s = request.getSession(false);
    if (s != null && s.getAttribute("user") != null) {
        Object countObj = s.getAttribute("cartCount");
        if (countObj instanceof Integer) {
            cartCount = (Integer) countObj;
        } else if (s.getAttribute("userCartId") instanceof Integer) {
            int cartId = (Integer) s.getAttribute("userCartId");
            if (cartId != -1) {
                List<CartItemDTO> items = CartService.getInstance().getCartItems(cartId);
                cartCount = items.stream().mapToInt(item -> item.getQuantity() == null ? 0 : item.getQuantity()).sum();
                s.setAttribute("cartCount", cartCount);
            }
        }
    }
%>
<header class="navbar navbar-expand-md sticky-top bg-card-glass border-bottom px-lg-4">
    <div class="container-fluid max-w-7xl d-flex align-items-center">
        <a class="navbar-brand d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/user/home">
            <div class="brand-logo-circle"><span class="font-serif">S</span></div>
            <span class="brand-text font-serif fw-bold h4 mb-0">Sweet Delights</span>
        </a>

        <div class="collapse navbar-collapse justify-content-center order-3 order-md-2" id="navbarNav">
            <ul class="navbar-nav gap-md-4 py-3 py-md-0 text-center text-md-start">
                <li class="nav-item">
                    <a class="nav-link text-muted hover-dark font-medium small" href="${pageContext.request.contextPath}/user/home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-muted hover-dark font-medium small" href="${pageContext.request.contextPath}/user/products">Shop</a>
                </li>
            </ul>
        </div>

        <div class="d-flex align-items-center gap-2 ms-auto order-2 order-md-3">
            <a href="${pageContext.request.contextPath}/user/cart" class="btn btn-icon position-relative">
                <i class="bi bi-cart3 fs-5"></i>
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                      style="font-size: 0.65em; display: <%= cartCount > 0 ? "inline-block" : "none" %>;">
                    <%= cartCount %>
                </span>
            </a>
            <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-icon">
                <i class="bi bi-person fs-5"></i>
            </a>
            <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-icon d-none d-md-inline-flex">
                <i class="bi bi-box-arrow-right fs-5"></i>
            </a>
        </div>
    </div>
</header>