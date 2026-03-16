<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Jakarta EE 10 / Tomcat 11 --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<h4 class="fw-bold mb-4">Orders (${orders.size()})</h4>

<div class="d-flex flex-column gap-3">
    <c:forEach var="order" items="${orders}">
        <div class="custom-card">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <span class="fw-bold text-dark">Order #order-${order.orderId}</span>
                    <span class="text-muted-custom ms-2" style="font-size: 0.9rem;">by ${order.customerName}</span>
                </div>
                <div class="d-flex align-items-center gap-3">
                    <span class="text-muted-custom" style="font-size: 0.9rem;">
                        <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy" />
                    </span>
                    <span class="text-primary fw-bold">$${order.totalPrice}</span>
                </div>
            </div>
            
            <div class="d-flex flex-wrap">
                <c:forEach var="item" items="${order.orderItems}">
                    <div class="order-item-pill">
                        ${item.productName} x${item.quantity} ($${item.price})
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>