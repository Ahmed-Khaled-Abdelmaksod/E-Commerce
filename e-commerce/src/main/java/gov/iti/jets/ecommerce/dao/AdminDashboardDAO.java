package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.beans.dashboard.OrderDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.UserDashboardBean;

import java.util.List;

// 🟢 الـ DAO ده مسؤوليته الوحيدة يجيب الداتا مجمعة وجاهزة للـ Dashboard
public interface AdminDashboardDAO {
    
    List<ProductDashboardBean> getAllProductsForDashboard();
    
    List<UserDashboardBean> getAllUsersForDashboard();
    
    List<OrderDashboardBean> getAllOrdersForDashboard();
    
    // ممكن مستقبلاً تضيف هنا Methods تجيب الإحصائيات (Counts, Total Revenue, etc.)
    // int getTotalProductsCount();
}