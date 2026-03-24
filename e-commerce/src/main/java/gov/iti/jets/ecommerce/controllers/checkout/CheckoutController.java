package gov.iti.jets.ecommerce.controllers.checkout;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.beans.checkout.CheckoutBean;
import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.service.CheckoutService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    private CheckoutService checkoutService;

    @Override
    public void init() throws ServletException {
        this.checkoutService = ServiceLocator.getInstance().getCheckoutService();
    }

    private Integer getUserIdFromSession(HttpSession session) {
        if (session == null) return null;
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj != null) {
            return (Integer) userIdObj;
        }
        UserBean user = (UserBean) session.getAttribute("user");
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = getUserIdFromSession(session);

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // --- Modification here: Check success status first before checking the cart ---
        String status = request.getParameter("status");
        if ("success".equals(status)) {
            if (session != null) {
                session.setAttribute("cartCount", 0);
            }
            // Forward the user to the new success page
            request.getRequestDispatcher("/views/checkout/success.jsp").forward(request, response);
            return;
        }

        CheckoutBean checkoutBean = checkoutService.getCheckoutDetails(userId);

        if (checkoutBean == null) {
            response.sendRedirect(request.getContextPath() + "/user/cart");
            return;
        }

        request.setAttribute("checkoutData", checkoutBean);
        request.getRequestDispatcher("/views/checkout/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = getUserIdFromSession(session);

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean isSuccess = checkoutService.processCheckout(userId);

        if (isSuccess) {
            // Keep UI cart state in sync on all pages after successful checkout.
            if (session != null) {
                session.setAttribute("cartCount", 0);
            }
            response.sendRedirect(request.getContextPath() + "/checkout?status=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/checkout?error=failed");
        }
    }
}