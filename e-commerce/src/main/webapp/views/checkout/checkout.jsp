<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Sweet Delights</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <style>
        /* تنسيق بسيط للزر المعطل فقط */
        .btn-pay:disabled {
            background-color: #d1d5db !important;
            color: #6b7280 !important;
            cursor: not-allowed;
            opacity: 1;
        }
    </style>
</head>
<body>

    <%@include file="/static/html/header.html"%>

    <main class="flex-grow-1 py-5">
        <div class="container max-w-7xl">

            <c:if test="${param.error == 'failed'}">
                <div class="alert alert-danger alert-dismissible fade show rounded-3 mb-4" role="alert">
                    <i class="bi bi-exclamation-circle-fill me-2"></i> Checkout failed. Please check your credit balance or product availability.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <a href="${pageContext.request.contextPath}/user/cart" class="text-decoration-none text-muted d-inline-block mb-3 hover-brand">
                <i class="bi bi-arrow-left me-1"></i> Back to Cart
            </a>

            <h1 class="h2 fw-bold font-serif mb-4 px-2">Checkout</h1>

            <div class="row g-4">

                <div class="col-lg-8">
                    <div class="d-flex flex-column gap-3">

                        <div class="card border rounded-3 p-4 shadow-sm">
                            <h5 class="fw-semibold mb-4">Delivery Details</h5>

                            <div class="d-flex justify-content-between mb-3 border-bottom pb-2">
                                <span class="text-muted small">Name</span>
                                <span class="fw-medium text-dark">${checkoutData.customerName}</span>
                            </div>
                            <div class="d-flex justify-content-between mb-3 border-bottom pb-2">
                                <span class="text-muted small">Email</span>
                                <span class="fw-medium text-dark">${checkoutData.customerEmail}</span>
                            </div>
                            <div class="d-flex justify-content-between mb-3 border-bottom pb-2">
                                <span class="text-muted small">Phone</span>
                                <span class="fw-medium text-dark">${not empty checkoutData.customerPhone ? checkoutData.customerPhone : 'N/A'}</span>
                            </div>
                            <div class="d-flex justify-content-between">
                                <span class="text-muted small">Address</span>
                                <span class="fw-medium text-dark">${not empty checkoutData.customerAddress ? checkoutData.customerAddress : 'N/A'}</span>
                            </div>
                        </div>

                        <div class="card border rounded-3 p-4 shadow-sm mt-2">
                            <h5 class="fw-semibold mb-4">Order Items</h5>

                            <div class="d-flex flex-column gap-3">
                                <c:forEach var="item" items="${checkoutData.orderItems}">
                                    <div class="d-flex align-items-center justify-content-between border-bottom pb-3 last-border-0">
                                        <div class="d-flex align-items-center gap-3">
                                            <div class="flex-shrink-0">
                                                <img src="${pageContext.request.contextPath}${item.imageUrl}"
                                                     alt="${item.productName}"
                                                     class="rounded-3 object-cover border"
                                                     style="width: 60px; height: 60px;">
                                            </div>
                                            <div>
                                                <div class="fw-semibold text-dark">${item.productName}</div>
                                                <div class="text-muted small">Qty: ${item.quantity}</div>
                                            </div>
                                        </div>
                                        <div class="fw-bold text-dark">
                                            <fmt:formatNumber value="${item.lineTotal}" pattern="$#,##0.00" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="card border rounded-3 p-4 shadow-sm sticky-top custom-sticky-offset">
                        <h2 class="h5 fw-semibold mb-4">Payment Summary</h2>

                        <div class="space-y-3">
                            <div class="d-flex justify-content-between small text-muted mb-2">
                                <span>Subtotal</span>
                                <span class="text-dark"><fmt:formatNumber value="${checkoutData.subtotal}" pattern="$#,##0.00" /></span>
                            </div>
                            <div class="d-flex justify-content-between small text-muted mb-2">
                                <span>Delivery</span>
                                <span class="text-dark">
                                    <c:choose>
                                        <c:when test="${checkoutData.deliveryFee == 0}">Free</c:when>
                                        <c:otherwise><fmt:formatNumber value="${checkoutData.deliveryFee}" pattern="$#,##0.00" /></c:otherwise>
                                    </c:choose>
                                </span>
                            </div>

                            <hr class="my-3 border-secondary-subtle">

                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <span class="fw-semibold">Total</span>
                                <span class="h5 fw-bold text-brand mb-0"><fmt:formatNumber value="${checkoutData.total}" pattern="$#,##0.00" /></span>
                            </div>

                            <p class="text-xs text-muted mb-4 small">
                                Credit available: <fmt:formatNumber value="${checkoutData.userCredit}" pattern="$#,##0.00" />
                            </p>

                            <form action="${pageContext.request.contextPath}/checkout" method="POST" class="m-0 p-0">
                                <button type="submit"
                                        class="btn btn-primary bg-brand btn-pay border-0 w-100 rounded-3 py-2 fw-medium d-flex align-items-center justify-content-center"
                                        ${checkoutData.userCredit < checkoutData.total ? 'disabled' : ''}>
                                    <i class="bi bi-credit-card me-2"></i>
                                    Pay <fmt:formatNumber value="${checkoutData.total}" pattern="$#,##0.00" />
                                </button>

                                <c:if test="${checkoutData.userCredit < checkoutData.total}">
                                    <div class="text-danger small mt-2 text-center fw-medium">
                                        <i class="bi bi-exclamation-triangle-fill me-1"></i> Insufficient credit balance.
                                    </div>
                                </c:if>
                            </form>
                        </div>
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