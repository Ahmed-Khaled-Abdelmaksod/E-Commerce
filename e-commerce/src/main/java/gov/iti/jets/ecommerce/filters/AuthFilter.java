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
        
        // Check if accessing admin paths
        String requestPath = request.getRequestURI();
        if (requestPath.contains("/admin/")) {
            // Verify user has ADMIN role
            UserRole userRole = (UserRole) session.getAttribute("userRole");
            if (userRole != UserRole.ADMIN) {
                // User is logged in but not an admin - redirect to home
                response.sendRedirect(request.getContextPath() + "/user/home");
                return;
            }
        }
        
        // All checks passed - allow request to continue
        chain.doFilter(request, response);
    }
}
