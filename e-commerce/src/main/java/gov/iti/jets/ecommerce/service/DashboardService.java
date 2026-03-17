package gov.iti.jets.ecommerce.service;


import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.beans.dashboard.CustomerBean;
import gov.iti.jets.ecommerce.beans.dashboard.OrderBean;
import gov.iti.jets.ecommerce.DTO.ProductDTO;

import java.util.List;

public interface DashboardService {

//    List<ProductBean> getAllProductsForDashboard();
//

    List<ProductDTO> getAllProductsForDashboard();
    List<CategoryDTO> getAllCategoriesForDashboard();
    List<OrderBean> getAllOrdersForDashboard();

    List<CustomerBean> getAllCustomersForDashboard();
}