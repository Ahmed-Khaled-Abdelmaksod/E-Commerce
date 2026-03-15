package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.service.AuthService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/login")
public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/views/signin.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        AuthService authService = AuthService.getInstance();
        Optional<UserBean> userBean = authService.login(email,password);
        if(userBean.isPresent()) {
            HttpSession session = req.getSession(true);
            session.setAttribute("loggedIn",new String("true"));
            session.setAttribute("user",userBean.get());
            if(userBean.get().getRole() == UserRole.ADMIN) {
                // TODO forward TO admin dashboard
            }else {
                resp.sendRedirect(req.getContextPath()+"/views/home.jsp"); // TODO handle the right link
            }
        }else {
            req.setAttribute("errorMessage","Invalid email or password.");
            req.getRequestDispatcher("/views/signin.jsp").forward(req, resp);
        }
    }
}
