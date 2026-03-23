<%--
  Created by IntelliJ IDEA.
  User: ak490
  Date: 3/5/2026
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
        <%--    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;500;600&display=swap" rel="stylesheet">--%>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    </head>
    <body>
        <%@include file="/static/html/header.html"%>
        <jsp:useBean id="cartItems" scope="request" class="java.util.ArrayList"/> <%-- TODO this will be adjusted to the real variable and use type instead of class attr--%>
        <c:choose>
            <c:when test="${empty cartItems}">
                <%@include file="/static/html/empty-cart.html"%>
            </c:when>
            <c:otherwise> <%-- TODO adjust functionality and use foreach and so on --%>
                <main class="flex-grow-1 py-5">
                    <div class="container max-w-7xl">
                        <h1 class="h2 fw-bold font-serif mb-4 px-2">Shopping Cart</h1>

                        <div class="row g-4">
                            <div class="col-lg-8">
                                <div class="d-flex flex-column gap-3">
                                    <c:forEach var="item" items="${cartItems}">
                                        <div class="card border rounded-3 p-3 shadow-sm">
                                            <div class="d-flex gap-4">
                                                <div class="flex-shrink-0">
                                                    <img src="${pageContext.request.contextPath}/static${item.productImage}"
                                                         alt="${item.productName}"
                                                         class="rounded-3 object-cover"
                                                         style="width: 100px; height: 100px;">
                                                </div>

                                                <div class="flex-grow-1 d-flex flex-column justify-content-between">
                                                    <div class="d-flex justify-content-between">
                                                        <div>
                                                            <a href="#" class="text-decoration-none fw-semibold text-dark hover-brand">${item.productName}</a>
                                                            <p class="text-brand fw-bold mb-0">$${item.unitPrice}</p>
                                                        </div>
                                                    </div>

                                                    <div class="d-flex align-items-center justify-content-between">
                                                        <div class="d-flex align-items-center rounded-3 border" style="width: fit-content;">
                                                            <button class="btn btn-sm border-0 px-2 py-1 text-muted hover-dark"
                                                                    onclick="updateQuantity(${item.cartItemId}, 'minus')">
                                                                <i class="bi bi-dash"></i>
                                                            </button>

                                                            <span class="px-3 fw-medium" id="qty-${item.cartItemId}">1</span>

                                                            <button class="btn btn-sm border-0 px-2 py-1 text-muted hover-dark"
                                                                    onclick="updateQuantity(${item.cartItemId}, 'plus')">
                                                                <i class="bi bi-plus"></i>
                                                            </button>
                                                        </div>

                                                        <button class="btn btn-light btn-sm text-danger rounded-3 p-2 border-0">
                                                            <i class="bi bi-trash3 fs-6"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="col-lg-4">
                                <div class="card border rounded-3 p-4 shadow-sm sticky-top custom-sticky-offset" >
                                    <h2 class="h5 fw-semibold mb-4">Order Summary</h2>
                                    <c:set var="grandTotal" value="${0.0}" />
                                    <div class="space-y-3">
                                        <c:forEach var="item" items="${cartItems}">
                                            <div class="d-flex justify-content-between small text-muted mb-2">
                                                <span>${item.productName} x${item.quantity}</span>
                                                <span class="text-dark">$${item.unitPrice}</span>
                                            </div>
                                            <c:set var="grandTotal" value="${grandTotal + item.lineTotal}" />
                                        </c:forEach>
                                        <hr class="my-3 border-secondary-subtle">

                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <span class="fw-semibold">Total</span>
                                            <span class="h5 fw-bold text-brand mb-0">$${grandTotal}</span>
                                        </div>
                                        <jsp:useBean id="user" class="gov.iti.jets.ecommerce.beans.UserBean"/>
                                        <p class="text-xs text-muted mb-4 small">Credit available: ${user.creditBalance}</p>

                                        <a href="/checkout" class="btn btn-primary bg-brand border-0 w-100 rounded-3 py-2 fw-medium d-flex align-items-center justify-content-center">
                                            Proceed to Checkout
                                            <i class="bi bi-arrow-right ms-2"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </c:otherwise>
        </c:choose>
        <%@include file="/static/html/footer.html"%>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/main.js"></script>
    </body>
</html>
