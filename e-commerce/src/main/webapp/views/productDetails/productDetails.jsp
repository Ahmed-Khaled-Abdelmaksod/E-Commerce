<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} - Sweet Delights</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <style>
        body {
            background-color:  #f8f5f2;
            color: var(--foreground);
            font-family: var(--font-sans);
        }

        /* ── Back link ── */
        .back-link {
            color: #7A6A6A;
            text-decoration: none;
            font-size: 0.88rem;
            font-family: var(--font-sans);
            transition: color 0.2s;
            display: inline-flex;
            align-items: center;
            gap: 0.35rem;
        }
        .back-link:hover { color: #C9536A; }
        .back-link i { font-size: 0.85rem; }

        /* ── Product image ── */
        .product-image-wrapper {
            border-radius: 1.1rem;
            overflow: hidden;
            background-color: #ddd9d3;
        }
        .product-image-wrapper img {
            width: 100%;
            height: auto;
            display: block;
            object-fit: cover;
        }

        /* ── Right column wrapper ── */
        .product-details {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            padding-top: 0.5rem;
        }

        /* ── Category badge ── */
        .badge-category {
            display: inline-block;
            background-color: rgba(201, 83, 106, 0.1);
            color: #C9536A;
            font-size: 0.8rem;
            font-weight: 500;
            padding: 0.25rem 0.85rem;
            border-radius: 999px;
            font-family: var(--font-sans);
            border: 1px solid rgba(201, 83, 106, 0.18);
        }

        /* ── Title ── */
        .product-title {
            font-family: var(--font-serif);
            font-size: 2.5rem;
            color: #2D1A1A;
            line-height: 1.15;
            font-weight: 700;
            margin: 0;
        }

        /* ── Description ── */
        .product-description {
            color: #5C4F4F;
            font-size: 1rem;
            line-height: 1.8;
            font-family: var(--font-sans);
            margin: 0;
        }

        /* ── Stock info ── */
        .stock-info {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: #5C4F4F;
            font-size: 0.93rem;
            font-family: var(--font-sans);
        }
        .stock-info i { font-size: 1rem; }

        /* ── Price ── */
        .product-price {
            font-size: 2.2rem;
            font-weight: 700;
            color: #C9536A;
            font-family: var(--font-sans);
            margin: 0;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:useBean id="role" scope="request" class="java.lang.String"/>
    <c:choose>
        <c:when test="${role == 'admin'}">
            <%@ include file="/static/html/admin-header.jsp"%>
        </c:when>
        <c:when test="${role == 'customer'}">
            <%@include file="/static/html/header.html"%>
        </c:when>
        <c:otherwise>
            <%@ include file="/static/html/guest-header.html"%>
        </c:otherwise>
    </c:choose>
    <main class="flex-grow-1 py-5">
        <div class="container" style="max-width: 1100px;">

            <%-- Back link --%>
            <a href="javascript:history.back();" class="back-link mb-4 d-inline-flex">
                <i class="bi bi-arrow-left"></i> Back to Shop
            </a>

            <div class="row g-5 align-items-start mt-1">

                <%-- LEFT: Product Image --%>
                <div class="col-12 col-lg-6">
                    <div class="product-image-wrapper">
                        <img src="${pageContext.request.contextPath}${product.imageUrl}"
                             alt="${product.name}">
                    </div>
                </div>

                <%-- RIGHT: Product Details --%>
                <div class="col-12 col-lg-6">
                    <div class="product-details">

                        <%-- Category badge --%>
                        <div>
                            <span class="badge-category">${product.categoryName}</span>
                        </div>

                        <%-- Title --%>
                        <h1 class="product-title">${product.name}</h1>

                        <%-- Description --%>
                        <p class="product-description">${product.description}</p>

                        <%-- Stock --%>
                        <div class="stock-info">
                            <i class="bi bi-box-seam"></i>
                            <span>
                                <c:choose>
                                    <c:when test="${product.stockQuantity > 0}">
                                        ${product.stockQuantity} in stock
                                    </c:when>
                                    <c:otherwise>
                                        Out of stock
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>

                        <%-- Price --%>
                        <div class="product-price">
                            <fmt:formatNumber value="${product.price}" pattern="$#,##0.00"/>
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
