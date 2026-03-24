<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Jakarta EE 10 / Tomcat 11 --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<h4 class="font-serif fw-bold mb-4" style="font-size: 1.35rem; letter-spacing: -0.3px; color: var(--foreground);">
    Customers (${customers.size()})
</h4>

<div class="row g-4">
    <c:forEach var="customer" items="${customers}">
        <div class="col-md-4">
            <div class="custom-card" style="border-radius: var(--radius);">

                <%-- Avatar + Name --%>
                <div class="d-flex align-items-center gap-3 mb-4">
                    <div class="avatar-circle">
                        <i class="bi bi-person" style="font-size: 1.1rem;"></i>
                    </div>
                    <div>
                        <h6 class="fw-bold mb-0" style="font-size: 0.97rem; color: var(--foreground);">
                            ${customer.fullName}
                        </h6>
                        <small class="text-muted-custom" style="font-size: 0.82rem;">
                            ID: user-${customer.userId}
                        </small>
                    </div>
                </div>

                <%-- Contact details --%>
                <div class="d-flex flex-column gap-2" style="font-size: 0.92rem; color: var(--muted-foreground);">

                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-envelope" style="font-size: 0.9rem; flex-shrink: 0;"></i>
                        <span>${customer.email}</span>
                    </div>

                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-telephone" style="font-size: 0.9rem; flex-shrink: 0;"></i>
                        <span>${not empty customer.phone ? customer.phone : 'N/A'}</span>
                    </div>

                    <div class="d-flex align-items-center gap-2">
                        <i class="bi bi-geo-alt" style="font-size: 0.9rem; flex-shrink: 0;"></i>
                        <span>${not empty customer.address ? customer.address : 'N/A'}</span>
                    </div>

                    <div class="d-flex align-items-center gap-2 mt-1">
                        <i class="bi bi-credit-card" style="color: var(--primary); font-size: 0.9rem; flex-shrink: 0;"></i>
                        <span class="fw-bold" style="color: var(--primary);">$${customer.creditBalance}</span>
                        <span style="color: var(--muted-foreground);">credit</span>
                    </div>

                </div>
            </div>
        </div>
    </c:forEach>
</div>
