package gov.iti.jets.ecommerce.filters;

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
        if (isLoggedIn) {
            // User is valid!
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth/login");
        }
    }
}
