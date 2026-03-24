package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.service.DashboardService;
import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;
import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.beans.dashboard.CustomerBean;
import gov.iti.jets.ecommerce.beans.dashboard.OrderBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private DashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = ServiceLocator.getInstance().getDashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tab = request.getParameter("tab");
        String requestedWith = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(requestedWith);

        // 1. Handle Full Page Loads
        if (!isAjax) {
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
            return;
        }

        // 2. Handle AJAX Tab Loading
        if (tab == null || tab.isEmpty()) {
            tab = "products";
        }

        switch (tab) {
            case "products":
                loadProductsTab(request, response);
                break;
            case "customers":
                loadCustomersTab(request, response);
                break;
            case "orders":
                loadOrdersTab(request, response);
                break;
            default:
                loadProductsTab(request, response);
                break;
        }
    }

    private void loadProductsTab(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ProductBean> products = dashboardService.getAllProductsForDashboard();
            List<CategoryDTO> categories = dashboardService.getAllCategoriesForDashboard();

            // Debugging
            System.out.println("Service DEBUG: Found " + (products != null ? products.size() : 0) + " products");

            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/views/admin/fragments/products-tab.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in loadProductsTab: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching product data.");
        }
    }

    private void loadCustomersTab(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CustomerBean> customers = dashboardService.getAllCustomersForDashboard();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/views/admin/fragments/customers-tab.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in loadCustomersTab: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching customer data.");
        }
    }

    private void loadOrdersTab(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<OrderBean> orders = dashboardService.getAllOrdersForDashboard();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/views/admin/fragments/orders-tab.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in loadOrdersTab: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching order data.");
        }
    }
}