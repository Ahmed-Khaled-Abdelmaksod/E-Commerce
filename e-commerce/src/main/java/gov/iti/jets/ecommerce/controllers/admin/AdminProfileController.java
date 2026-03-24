package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.service.ProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/profile")
public class AdminProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        UserBean user = (UserBean) session.getAttribute("user");
        List<Order> orders = ProfileService.getInstance().getOrdersByUserId(user.getUserId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/admin-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        UserBean currentUser = (UserBean) session.getAttribute("user");

        UserBean updatedBean = new UserBean();
        updatedBean.setUserId(currentUser.getUserId());

        String fullName    = req.getParameter("fullName");
        String phone       = req.getParameter("phone");
        String address     = req.getParameter("address");
        String birthdayStr = req.getParameter("birthday");

        updatedBean.setFullName(fullName);
        updatedBean.setPhone(phone);
        updatedBean.setAddress(address);

        if (birthdayStr != null && !birthdayStr.isBlank()) {
            try { updatedBean.setBirthday(LocalDate.parse(birthdayStr)); }
            catch (Exception e) { updatedBean.setBirthday(currentUser.getBirthday()); }
        } else {
            updatedBean.setBirthday(currentUser.getBirthday());
        }

        ProfileService.ProfileResult result = ProfileService.getInstance().updateProfile(updatedBean);
        if (result.isSuccess()) {
            session.setAttribute("user", result.getUser());
            resp.sendRedirect(req.getContextPath() + "/admin/profile?updated=true");
        } else {
            req.setAttribute("errorMessage", result.getErrorMessage());
            List<Order> orders = ProfileService.getInstance().getOrdersByUserId(currentUser.getUserId());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/views/admin-profile.jsp").forward(req, resp);
        }
    }
}