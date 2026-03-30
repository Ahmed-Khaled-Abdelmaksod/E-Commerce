package gov.iti.jets.ecommerce.controllers.productDetails;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.beans.productDetails.ProductDetailsBean;
import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.enums.UserRole;
import gov.iti.jets.ecommerce.service.ProductDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/product/details")
public class ProductDetailsController extends HttpServlet {

    private ProductDetailsService productDetailsService;

    @Override
    public void init() throws ServletException {
        this.productDetailsService = ServiceLocator.getInstance().getProductDetailsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Extract productId from request parameter
        String productIdParam = request.getParameter("productId");

        // 2. Validate that productId was provided
        if (productIdParam == null || productIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/products");
            return;
        }

        // 3. Validate that productId is a valid integer
        int productId;
        try {
            productId = Integer.parseInt(productIdParam.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/user/products");
            return;
        }

        // 4. Fetch product details from the service layer
        ProductDetailsBean productDetails = productDetailsService.getProductDetailsById(productId);

        // 5. If product not found, redirect back to shop
        if (productDetails == null) {
            response.sendRedirect(request.getContextPath() + "/user/products");
            return;
        }

        // 6. Pass the bean to the JSP and forward
        request.setAttribute("product", productDetails);

        // 7. check if the admin hit the product details
        UserBean userBean = (UserBean) request.getSession(false).getAttribute("user");
        if (userBean != null) {
            request.setAttribute("role", userBean.getRole().name().toLowerCase());
        }
        request.getRequestDispatcher("/views/productDetails/productDetails.jsp")
               .forward(request, response);
    }
}
