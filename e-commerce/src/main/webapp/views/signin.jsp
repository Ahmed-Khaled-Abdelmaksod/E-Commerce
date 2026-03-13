<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sweet Delights - Sign In</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/auth.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-4">
            <div class="card p-4">
                <div class="card-body p-0">
                    <h3 class="card-title fw-bold text-brand mb-1">Welcome Back</h3>
                    <p class="text-muted small mb-4">Sign in to your Sweet Delights account</p>
                    <div id="error-container" class="alert alert-danger ${empty requestScope.errorMessage ? 'd-none' : ''} small py-2" role="alert">
                        <c:if test="${not empty requestScope.errorMessage}">
                            ${requestScope.errorMessage}
                        </c:if>
                    </div>
                    <form action="${pageContext.request.contextPath}/login" method="POST">
                        <div class="mb-3">
                            <label for="email" class="form-label small fw-medium text-brand">Email</label>
                            <input type="email" class="form-control form-control-sm py-2" id="email" name="email" placeholder="you@example.com" required>
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label small fw-medium text-brand">Password</label>
                            <input type="password" class="form-control form-control-sm py-2" id="password" name="password" placeholder="••••••••" required>
                        </div>
                        <button type="submit" class="btn btn-brand w-100 py-2">Sign In</button>
                    </form>

                    <div class="text-center mt-4 pt-2">
                        <p class="small text-muted mb-0">Don't have an account? <a href="signup.jsp" class="link-brand">Sign up</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<%-- For Salah : http://localhost:8080/e_commerce_war/views/signin.jsp --%>