package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.dashboard.CustomerBean;
import gov.iti.jets.ecommerce.beans.dashboard.OrderBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;

import java.util.List;

public interface DashboardService {
    
    List<ProductBean> getAllProductsForDashboard();
    
    List<OrderBean> getAllOrdersForDashboard();
    
    List<CustomerBean> getAllCustomersForDashboard();
}