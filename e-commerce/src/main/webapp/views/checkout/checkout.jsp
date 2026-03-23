<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Sweet Delights</title>
    
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    
    <link href="${pageContext.request.contextPath}/static/css/globals.css" rel="stylesheet">

    <style>
        .checkout-card {
            background-color: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.02);
        }

        .product-img {
            width: 55px;
            height: 55px;
            object-fit: cover;
            border-radius: calc(var(--radius) - 4px);
            background-color: var(--muted);
            border: 1px solid var(--border);
        }

        .divider {
            height: 1px;
            background-color: var(--border);
            margin: 1.2rem 0;
        }

        .btn-pay {
            background-color: var(--primary);
            color: var(--primary-foreground);
            border: none;
            border-radius: var(--radius);
            font-weight: 500;
            transition: opacity 0.2s ease;
        }

        .btn-pay:hover {
            opacity: 0.9;
            color: var(--primary-foreground);
        }
        
        .back-link {
            text-decoration: none;
            transition: color 0.2s ease;
        }
        
        .back-link:hover {
            color: var(--foreground) !important;
        }
    </style>
</head>
<body class="font-sans">

    <jsp:include page="/static/html/header.html" />

    <div class="container py-4 py-md-5" style="max-width: 1000px;">
        
        <c:if test="${param.error == 'failed'}">
            <div class="alert alert-danger alert-dismissible fade show border-0 rounded-4 mb-4" style="background-color: var(--destructive); color: var(--destructive-foreground);" role="alert">
                <i class="bi bi-exclamation-circle-fill me-2"></i> Checkout failed. Please check your credit balance or cart items.
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/cart" class="back-link text-muted-custom d-inline-block mb-3 fs-6">
            <i class="bi bi-arrow-left me-1"></i> Back to Cart
        </a>

        <h1 class="font-serif fw-bold mb-4" style="font-size: clamp(1.8rem, 4vw, 2.2rem); letter-spacing: -0.5px;">Checkout</h1>

        <div class="row g-4">
            
            <div class="col-12 col-lg-7 d-flex flex-column gap-4">
                
                <div class="checkout-card p-3 p-md-4">
                    <h5 class="fw-semibold mb-4" style="color: var(--foreground); font-size: 1.15rem;">Delivery Details</h5>
                    
                    <div class="d-flex justify-content-between mb-3 fs-6">
                        <span class="text-muted-custom">Name</span>
                        <span class="fw-medium text-end" style="color: var(--foreground);">${checkoutData.customerName}</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3 fs-6">
                        <span class="text-muted-custom">Email</span>
                        <span class="fw-medium text-end" style="color: var(--foreground);">${checkoutData.customerEmail}</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3 fs-6">
                        <span class="text-muted-custom">Phone</span>
                        <span class="fw-medium text-end" style="color: var(--foreground);">${not empty checkoutData.customerPhone ? checkoutData.customerPhone : 'N/A'}</span>
                    </div>
                    <div class="d-flex justify-content-between fs-6">
                        <span class="text-muted-custom">Address</span>
                        <span class="fw-medium text-end" style="color: var(--foreground);">${not empty checkoutData.customerAddress ? checkoutData.customerAddress : 'N/A'}</span>
                    </div>
                </div>

                <div class="checkout-card p-3 p-md-4">
                    <h5 class="fw-semibold mb-4" style="color: var(--foreground); font-size: 1.15rem;">Order Items</h5>
                    
                    <div class="d-flex flex-column gap-3">
                        <c:forEach var="item" items="${checkoutData.orderItems}">
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="d-flex align-items-center gap-3">
                                    <img src="${pageContext.request.contextPath}${item.imageUrl}" alt="${item.productName}" class="product-img flex-shrink-0">
                                    <div>
                                        <div class="fw-medium text-break" style="color: var(--foreground); font-size: 0.95rem;">${item.productName}</div>
                                        <div class="text-muted-custom" style="font-size: 0.85rem;">Qty: ${item.quantity}</div>
                                    </div>
                                </div>
                                <div class="fw-medium ms-3" style="color: var(--foreground);">
                                    <fmt:formatNumber value="${item.lineTotal}" pattern="$#,##0.00" />
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

            </div>

            <div class="col-12 col-lg-5">
                <div class="position-sticky" style="top: 2rem;">
                    <div class="checkout-card p-3 p-md-4">
                        <h5 class="fw-semibold mb-4" style="color: var(--foreground); font-size: 1.15rem;">Payment Summary</h5>
                        
                        <div class="d-flex justify-content-between mb-3 fs-6">
                            <span class="text-muted-custom">Subtotal</span>
                            <span class="fw-medium" style="color: var(--foreground);">
                                <fmt:formatNumber value="${checkoutData.subtotal}" pattern="$#,##0.00" />
                            </span>
                        </div>
                        <div class="d-flex justify-content-between mb-3 fs-6">
                            <span class="text-muted-custom">Delivery</span>
                            <span class="fw-medium" style="color: var(--foreground);">
                                <c:choose>
                                    <c:when test="${checkoutData.deliveryFee == 0}">Free</c:when>
                                    <c:otherwise><fmt:formatNumber value="${checkoutData.deliveryFee}" pattern="$#,##0.00" /></c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        
                        <div class="divider"></div>
                        
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <span class="fw-bold fs-5" style="color: var(--foreground);">Total</span>
                            <span class="fw-bold fs-5 text-primary">
                                <fmt:formatNumber value="${checkoutData.total}" pattern="$#,##0.00" />
                            </span>
                        </div>
                        
                        <div class="divider"></div>
                        
                        <div class="d-flex justify-content-between fs-6">
                            <span class="text-muted-custom">Your credit</span>
                            <span class="fw-medium" style="color: var(--foreground);">
                                <fmt:formatNumber value="${checkoutData.userCredit}" pattern="$#,##0.00" />
                            </span>
                        </div>

                        <form action="${pageContext.request.contextPath}/checkout" method="POST" class="mt-4">
                            <button type="submit" class="btn-pay w-100 py-3 d-flex justify-content-center align-items-center gap-2 fs-6">
                                <i class="bi bi-credit-card fs-5"></i>
                                Pay <fmt:formatNumber value="${checkoutData.total}" pattern="$#,##0.00" />
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>