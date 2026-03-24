package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.beans.dashboard.CustomerBean;
import gov.iti.jets.ecommerce.beans.dashboard.OrderBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;

import java.util.List;

public interface DashboardService {
    
    void saveProduct(ProductBean productBean);
    boolean deleteProduct(int productId);
    List<ProductBean> getAllProductsForDashboard(); 

    List<CategoryDTO> getAllCategoriesForDashboard();
    List<OrderBean> getAllOrdersForDashboard();
    List<CustomerBean> getAllCustomersForDashboard();
}