package gov.iti.jets.ecommerce.filters;

import gov.iti.jets.ecommerce.enums.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*","/admin/*"})
public class AuthFilter extends HttpFilter {
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && "true".equals(session.getAttribute("loggedIn")));
        
        if (!isLoggedIn) {
            // Not logged in - redirect to login
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        // Get user role
        UserRole userRole = (UserRole) session.getAttribute("userRole");
        String requestPath = request.getRequestURI();
        
        // Check if accessing admin paths
        if (requestPath.contains("/admin/")) {
            // Verify user has ADMIN role
            if (userRole != UserRole.ADMIN) {
                // User is logged in but not an admin - redirect to user home
                response.sendRedirect(request.getContextPath() + "/user/home");
                return;
            }
        }
        
        // Check if accessing user paths
        if (requestPath.contains("/user/")) {
            // Verify user has CUSTOMER role
            if (userRole == UserRole.ADMIN) {
                // Admin trying to access customer pages - redirect to admin dashboard
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }
        }
        
        // All checks passed - allow request to continue
        chain.doFilter(request, response);
    }
}
