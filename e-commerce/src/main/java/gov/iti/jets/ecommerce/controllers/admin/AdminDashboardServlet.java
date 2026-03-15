package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.service.DashboardService;
import gov.iti.jets.ecommerce.beans.dashboard.CustomerBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;
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

        // Main dashboard landing page
        if (tab == null || tab.isEmpty()) {
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
            return;
        }

        // Routing to tab-specific logic
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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid tab requested");
        }
    }

    private void loadProductsTab(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Using the new getAllProductsForDashboard method
        List<ProductBean> products = dashboardService.getAllProductsForDashboard();
        request.setAttribute("products", products);

        request.getRequestDispatcher("/views/admin/fragments/products-tab.jsp").forward(request, response);
    }

    private void loadCustomersTab(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Using CustomerBean list for the customers tab
        List<CustomerBean> customers = dashboardService.getAllCustomersForDashboard();
        request.setAttribute("customers", customers);

        request.getRequestDispatcher("/views/admin/fragments/customers-tab.jsp").forward(request, response);
    }

    private void loadOrdersTab(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Using OrderBean list (which contains nested OrderItemBeans)
        List<OrderBean> orders = dashboardService.getAllOrdersForDashboard();
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/views/admin/fragments/orders-tab.jsp").forward(request, response);
    }
}