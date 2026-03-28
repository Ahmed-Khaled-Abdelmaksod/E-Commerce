package gov.iti.jets.ecommerce.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = {"", "/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && "true".equals(session.getAttribute("loggedIn")));

        if (isLoggedIn) {
            resp.sendRedirect(req.getContextPath() + "/user/home");
        } else {
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
        }
    }
}
