<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sweet Delights - Create Account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/auth.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-md-10 col-lg-8">
            <div class="card p-4">
                <div class="card-body p-0">
                    <h3 class="card-title fw-bold text-brand mb-1">Create Account</h3>
                    <p class="text-muted small mb-4">Join Sweet Delights and start shopping today</p>
                    <div id="error-container" class="alert alert-danger ${empty requestScope.validationErrorMessage ? 'd-none' : ''} small py-2" role="alert">
                        <c:if test="${not empty requestScope.validationErrorMessage}">
                            ${requestScope.validationErrorMessage}
                        </c:if>
                    </div>
                    <form action="${pageContext.request.contextPath}/auth/register" method="POST" onsubmit="return validate()">
                        <c:if test="${not empty requestScope.checkout}">
                            <input type="hidden" name="checkout" value="true">
                        </c:if>

                        <h6 class="text-brand border-bottom pb-2 mb-3">Personal Information</h6>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="name" class="form-label small fw-medium text-brand">Full Name</label>
                                <input type="text" class="form-control form-control-sm py-2" id="name" name="name" placeholder="John Doe" required>
                                <div id="name-error" class="text-danger small mt-1 d-none"></div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="dob" class="form-label small fw-medium text-brand">Date of Birth</label>
                                <input type="date" class="form-control form-control-sm py-2" id="dob" name="dob" required>
                                <div id="dob-error" class="text-danger small mt-1 d-none">Please provide a valid date.</div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label small fw-medium text-brand">Email</label>
                                <input type="email" class="form-control form-control-sm py-2" id="email" name="email" placeholder="you@example.com" required>
                                <div id="email-error" class="text-danger small mt-1 d-none"></div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="phone" class="form-label small fw-medium text-brand">Phone Number</label>
                                <input type="tel" class="form-control form-control-sm py-2" id="phone" name="phoneNo" placeholder="+123456789" required>
                                <div id="phone-error" class="text-danger small mt-1 d-none">Invalid phone format.</div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-4">
                                <label for="password" class="form-label small fw-medium text-brand">Password</label>
                                <input type="password" class="form-control form-control-sm py-2" id="password" name="password" placeholder="••••••••" required>
                                <div id="password-error" class="text-danger small mt-1 d-none">Password is too weak.</div>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label for="confirm_password" class="form-label small fw-medium text-brand">Confirm Password</label>
                                <input type="password" class="form-control form-control-sm py-2" id="confirm_password" name="confirm_password" placeholder="••••••••" required>
                                <div id="match-error" class="text-danger small mt-1 d-none">Passwords do not match.</div>
                            </div>
                        </div>

                        <h6 class="text-brand border-bottom pb-2 mb-3">Address Details</h6>
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="houseNo" class="form-label small fw-medium text-brand">House No.</label>
                                <input type="text" class="form-control form-control-sm py-2" id="houseNo" name="houseNo" required>
                            </div>
                            <div class="col-md-8 mb-3">
                                <label for="street" class="form-label small fw-medium text-brand">Street</label>
                                <input type="text" class="form-control form-control-sm py-2" id="street" name="street" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4 mb-4">
                                <label for="area" class="form-label small fw-medium text-brand">Area</label>
                                <input type="text" class="form-control form-control-sm py-2" id="area" name="area" required>
                            </div>
                            <div class="col-md-4 mb-4">
                                <label for="city" class="form-label small fw-medium text-brand">City</label>
                                <input type="text" class="form-control form-control-sm py-2" id="city" name="city" required>
                            </div>
                            <div class="col-md-4 mb-4">
                                <label for="country" class="form-label small fw-medium text-brand">Country</label>
                                <input type="text" class="form-control form-control-sm py-2" id="country" name="country" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-brand w-100 py-2">Create Account</button>
                    </form>

                    <div class="text-center mt-4 pt-2">
                        <p class="small text-muted mb-0">Already have an account? <a href="${pageContext.request.contextPath}/auth/login${not empty requestScope.checkout ? '?checkout=true' : ''}" class="link-brand">Sign in</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/signup.js"></script>
</body>
</html>