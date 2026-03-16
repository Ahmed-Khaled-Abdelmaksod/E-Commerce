<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Jakarta EE 10 / Tomcat 11 --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<h4 class="fw-bold mb-4">Customers (${customers.size()})</h4>

<div class="row g-4">
    <c:forEach var="customer" items="${customers}">
        <div class="col-md-4">
            <div class="custom-card">
                <div class="d-flex align-items-center gap-3 mb-4">
                    <div class="avatar-circle">
                        <i class="bi bi-person"></i>
                    </div>
                    <div>
                        <h6 class="fw-bold mb-0">${customer.fullName}</h6>
                        <small class="text-muted-custom">ID: user-${customer.userId}</small>
                    </div>
                </div>
                
                <div class="d-flex flex-column gap-2 text-muted-custom" style="font-size: 0.95rem;">
                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-envelope"></i> ${customer.email}
                    </div>
                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-telephone"></i> ${not empty customer.phone ? customer.phone : 'N/A'}
                    </div>
                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-geo-alt"></i> ${not empty customer.address ? customer.address : 'N/A'}
                    </div>
                    <div class="d-flex align-items-center gap-2 mt-1">
                        <i class="bi bi-credit-card text-primary"></i> 
                        <span class="text-primary fw-bold">$${customer.creditBalance}</span> credit
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>