package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/admin/profile")
public class AdminProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            req.getRequestDispatcher("/views/admin-profile.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
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
        updatedBean.setUserId(currentUser.getUserId()); // always from session

        String fullName  = req.getParameter("fullName");
        String phone     = req.getParameter("phone");
        String address   = req.getParameter("address");
        String birthdayStr = req.getParameter("birthday");

        updatedBean.setFullName(fullName);
        updatedBean.setPhone(phone);
        updatedBean.setAddress(address);

        if (birthdayStr != null && !birthdayStr.isBlank()) {
            try {
                updatedBean.setBirthday(LocalDate.parse(birthdayStr));
            } catch (Exception e) {
                updatedBean.setBirthday(currentUser.getBirthday());
            }
        } else {
            updatedBean.setBirthday(currentUser.getBirthday());
        }

        Optional<UserBean> result = AuthService.getInstance().updateProfile(updatedBean);
        if (result.isPresent()) {
            session.setAttribute("user", result.get());
            resp.sendRedirect(req.getContextPath() + "/admin/profile?updated=true");
        } else {
            req.setAttribute("errorMessage", "Could not update profile. Please try again.");
            req.getRequestDispatcher("/views/admin-profile.jsp").forward(req, resp);
        }
    }
}
