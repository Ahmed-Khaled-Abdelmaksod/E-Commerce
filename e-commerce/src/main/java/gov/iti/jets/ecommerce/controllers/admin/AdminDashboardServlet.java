package gov.iti.jets.ecommerce.controllers.admin;

import gov.iti.jets.ecommerce.context.ServiceLocator;
import gov.iti.jets.ecommerce.service.AdminDashboardService;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.UserDashboardBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private AdminDashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = ServiceLocator.getInstance().getAdminDashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String tab = request.getParameter("tab");

        if (tab == null || tab.isEmpty()) {

            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
            return;
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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid tab requested");
        }
    }

    private void loadProductsTab(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<ProductDashboardBean> products = dashboardService.getProductsView();
        request.setAttribute("products", products);

        request.getRequestDispatcher("/views/admin/fragments/products-tab.jsp").forward(request, response);
    }

    private void loadCustomersTab(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<UserDashboardBean> customers = dashboardService.getUsersView();
        request.setAttribute("customers", customers);

        request.getRequestDispatcher("/views/admin/fragments/customers-tab.jsp").forward(request, response);
    }

    private void loadOrdersTab(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<UserBean> orders = dashboardService.getOrdersView();
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/views/admin/fragments/orders-tab.jsp").forward(request, response);
    }
}