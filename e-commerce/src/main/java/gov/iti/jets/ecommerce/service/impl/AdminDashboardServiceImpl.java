package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.dao.AdminDashboardDAO;
import gov.iti.jets.ecommerce.service.AdminDashboardService;
import gov.iti.jets.ecommerce.beans.dashboard.OrderDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.UserDashboardBean;

import java.util.List;

/**
 * التنفيذ الفعلي لخدمة لوحة تحكم الإدمن.
 */
public final class AdminDashboardServiceImpl implements AdminDashboardService {

    // الاعتماد على AdminDashboardDAO للوصول لقاعدة البيانات
    private final AdminDashboardDAO dashboardDAO;

    // حقن الاعتمادية (Dependency Injection) عن طريق الـ Constructor
    public AdminDashboardServiceImpl(AdminDashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    @Override
    public List<ProductDashboardBean> getProductsView() {
        // يمكن إضافة أي Business Logic هنا مستقبلاً (مثل التحقق من الصلاحيات)
        return dashboardDAO.getAllProductsForDashboard();
    }

    @Override
    public List<UserDashboardBean> getUsersView() {
        return dashboardDAO.getAllUsersForDashboard();
    }

    @Override
    public List<OrderDashboardBean> getOrdersView() {
        return dashboardDAO.getAllOrdersForDashboard();
    }
}