package gov.iti.jets.ecommerce.controllers;

import gov.iti.jets.ecommerce.beans.SignUpBean;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.service.AuthService;
import gov.iti.jets.ecommerce.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/auth/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pass checkout param so the form can forward it
        String checkout = request.getParameter("checkout");
        if ("true".equals(checkout)) {
            request.setAttribute("checkout", "true");
        }
        request.getRequestDispatcher("/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fullName = req.getParameter("name");
        String email = req.getParameter("email");
        String password= req.getParameter("password") ;
        String phone = req.getParameter("phoneNo");
        String birthday = req.getParameter("dob");
        LocalDate dob = LocalDate.parse(birthday);
        String houseNo = req.getParameter("houseNo");
        String street = req.getParameter("street");
        String area = req.getParameter("area");
        String city = req.getParameter("city");
        String country = req.getParameter("country");
        String address = houseNo + " - " + street + " - " +area + " - " + city+" - " + country;


        SignUpBean signUpBean = new SignUpBean();

        signUpBean.setAddress(address);
        signUpBean.setBirthday(dob);
        signUpBean.setEmail(email);
        signUpBean.setCreditBalance((BigDecimal) req.getServletContext().getAttribute("defaultBalance"));

        signUpBean.setRole(UserRole.CUSTOMER);
        signUpBean.setPhone(phone);
        signUpBean.setFullName(fullName);
        signUpBean.setPassword(password);
        String errorMessage = AuthService.getInstance().signUp(signUpBean);
        if(errorMessage == null) { // success
            // Auto-login the new user
            Optional<UserBean> userBean = AuthService.getInstance().login(email, password);
            if (userBean.isPresent()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("loggedIn", "true");
                session.setAttribute("user", userBean.get());

                int userCartId = CartService.getInstance().getUserCart(userBean.get().getUserId());
                if (userCartId == -1) {
                    userCartId = CartService.getInstance().createCart(userBean.get().getUserId());
                }
                session.setAttribute("userCartId", userCartId);
                session.setAttribute("cartCount", 0);

                // Redirect with merge params
                String redirect = req.getContextPath() + "/user/home?mergeCart=true";
                String checkout = req.getParameter("checkout");
                if ("true".equals(checkout)) {
                    redirect += "&checkout=true";
                }
                resp.sendRedirect(redirect);
            } else {
                // Fallback — shouldn't happen since we just created the user
                resp.sendRedirect(req.getContextPath() + "/auth/login?success=accountCreated");
            }
        }else {
            req.setAttribute("validationErrorMessage", errorMessage);
            req.getRequestDispatcher("/views/signup.jsp").forward(req, resp);
        }
    }
}

