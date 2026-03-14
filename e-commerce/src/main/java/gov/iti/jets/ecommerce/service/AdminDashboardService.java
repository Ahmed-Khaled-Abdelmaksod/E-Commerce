package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.dashboard.OrderDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.UserDashboardBean;

import java.util.List;

/**
 * واجهة الخدمة الخاصة بلوحة تحكم الإدمن (Admin Dashboard).
 * مسؤولة عن تزويد الواجهة الأمامية بالبيانات المجمعة والجاهزة للعرض.
 */
public interface AdminDashboardService {

    /**
     * إرجاع قائمة بجميع المنتجات مع أسماء الأقسام الخاصة بها.
     * @return قائمة من ProductDashboardBean
     */
    List<ProductDashboardBean> getProductsView();

    /**
     * إرجاع قائمة بجميع المستخدمين (العملاء والإدمن) مع تفاصيلهم.
     * @return قائمة من UserDashboardBean
     */
    List<UserDashboardBean> getUsersView();

    /**
     * إرجاع قائمة بجميع الطلبات مجمعة مع تفاصيل العميل والمنتجات داخل كل طلب.
     * @return قائمة من OrderDashboardBean
     */
    List<OrderDashboardBean> getOrdersView();

}