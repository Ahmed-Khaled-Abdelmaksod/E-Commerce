package gov.iti.jets.ecommerce.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cart")
public class GuestCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && "true".equals(session.getAttribute("loggedIn")));

        if (isLoggedIn) {
            // Logged-in users go to the real cart
            resp.sendRedirect(req.getContextPath() + "/user/cart");
        } else {
            req.getRequestDispatcher("/views/guest-cart.jsp").forward(req, resp);
        }
    }
}
