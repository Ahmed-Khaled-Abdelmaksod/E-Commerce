package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.AdminDashboardDAO;
import gov.iti.jets.ecommerce.beans.dashboard.OrderDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.OrderItemDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.ProductDashboardBean;
import gov.iti.jets.ecommerce.beans.dashboard.UserDashboardBean;
import gov.iti.jets.ecommerce.enums.OrderStatus;
import gov.iti.jets.ecommerce.enums.UserRole;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AdminDashboardDaoImpl implements AdminDashboardDAO {

    private final DataSource dataSource;

    public AdminDashboardDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // ======================================================
    // SQL QUERIES (Mapped exactly to the provided Schema)
    // ======================================================
    
    // 🟢 استعلام المنتجات: ربطنا products مع categories عن طريق category_id
    private static final String SELECT_ALL_PRODUCTS_SQL =
            "SELECT p.product_id, p.name, p.price, p.stock_quantity, p.category_id, " +
            "p.image_url, c.name AS category_name " +
            "FROM products p " +
            "LEFT JOIN categories c ON p.category_id = c.category_id " +
            "ORDER BY p.created_at DESC";

    // 🟢 استعلام المستخدمين: هنجيب العملاء بس (لو عايز تجيب كله شيل الـ WHERE)
    private static final String SELECT_ALL_USERS_SQL =
            "SELECT user_id, full_name, email, phone, address, credit_balance, role " +
            "FROM users ORDER BY created_at DESC";

    // 🟢 استعلام الطلبات: مجمع فيه (الطلب + العميل + عناصر الطلب + اسم المنتج)
    // الـ Indexes اللي أنت عاملها (idx_orders_user, idx_order_items_order) هتخلي الـ Query دي سريعة جداً
    private static final String SELECT_ALL_ORDERS_SQL =
            "SELECT o.order_id, o.user_id, u.full_name AS customer_name, o.order_date, o.total_price, o.status, " +
            "oi.order_item_id, oi.product_id, p.name AS product_name, oi.quantity, oi.price AS item_price " +
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.user_id " +
            "LEFT JOIN order_items oi ON o.order_id = oi.order_id " +
            "LEFT JOIN products p ON oi.product_id = p.product_id " +
            "ORDER BY o.order_date DESC";

    // ======================================================
    // IMPLEMENTATIONS
    // ======================================================

    @Override
    public List<ProductDashboardBean> getAllProductsForDashboard() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<ProductDashboardBean> products = new ArrayList<>();
            while (rs.next()) {
                products.add(mapRowToProductBean(rs));
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching dashboard products", e);
        }
    }

    @Override
    public List<UserDashboardBean> getAllUsersForDashboard() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<UserDashboardBean> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapRowToUserBean(rs));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching dashboard users", e);
        }
    }

    @Override
    public List<OrderDashboardBean> getAllOrdersForDashboard() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_SQL);
             ResultSet rs = statement.executeQuery()) {

            // استخدمنا LinkedHashMap عشان نجمع كل الـ order_items تحت الـ order_id بتاعهم
            Map<Integer, OrderDashboardBean> orderMap = new LinkedHashMap<>();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                OrderDashboardBean orderBean = orderMap.get(orderId);
                if (orderBean == null) {
                    orderBean = mapRowToOrderBean(rs);
                    orderMap.put(orderId, orderBean);
                }

                // لو الطلب جواه عناصر (بسبب الـ LEFT JOIN)، ضيفها للـ List
                //int orderItemId = rs.getInt("order_item_id");
                if (!rs.wasNull()) {
                    orderBean.getOrderItems().add(mapRowToOrderItemBean(rs));
                }
            }

            return new ArrayList<>(orderMap.values());

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching dashboard orders", e);
        }
    }

    // ======================================================
    // MAPPING HELPERS
    // ======================================================

    private static ProductDashboardBean mapRowToProductBean(ResultSet rs) throws SQLException {
        ProductDashboardBean bean = new ProductDashboardBean();
        bean.setProductId(rs.getInt("product_id"));
        bean.setName(rs.getString("name"));
        bean.setPrice(rs.getBigDecimal("price"));
        bean.setStockQuantity(rs.getInt("stock_quantity"));
        bean.setCategoryId(rs.getInt("category_id"));
        bean.setCategoryName(rs.getString("category_name"));
        bean.setImageUrl(rs.getString("image_url"));
        return bean;
    }

    private static UserDashboardBean mapRowToUserBean(ResultSet rs) throws SQLException {
        UserDashboardBean bean = new UserDashboardBean();
        bean.setUserId(rs.getInt("user_id"));
        bean.setFullName(rs.getString("full_name"));
        bean.setEmail(rs.getString("email"));
        bean.setPhone(rs.getString("phone"));
        bean.setAddress(rs.getString("address"));
        bean.setCreditBalance(rs.getBigDecimal("credit_balance"));
        
        String roleValue = rs.getString("role");
        if (roleValue != null) {
            bean.setRole(UserRole.valueOf(roleValue.toUpperCase())); // Maps 'admin' -> ADMIN
        }
        return bean;
    }

    private static OrderDashboardBean mapRowToOrderBean(ResultSet rs) throws SQLException {
        OrderDashboardBean bean = new OrderDashboardBean();
        bean.setOrderId(rs.getInt("order_id"));
        bean.setUserId(rs.getInt("user_id"));
        bean.setCustomerName(rs.getString("customer_name"));
        bean.setOrderDate(rs.getTimestamp("order_date"));
        bean.setTotalPrice(rs.getBigDecimal("total_price"));
        
        String statusValue = rs.getString("status");
        if (statusValue != null) {
            bean.setStatus(OrderStatus.valueOf(statusValue.toUpperCase())); // Maps 'completed' -> COMPLETED
        }
        
        bean.setOrderItems(new ArrayList<>()); 
        return bean;
    }

    private static OrderItemDashboardBean mapRowToOrderItemBean(ResultSet rs) throws SQLException {
        OrderItemDashboardBean bean = new OrderItemDashboardBean();
        bean.setOrderItemId(rs.getInt("order_item_id"));
        bean.setProductId(rs.getInt("product_id"));
        bean.setProductName(rs.getString("product_name"));
        bean.setQuantity(rs.getInt("quantity"));
        bean.setPrice(rs.getBigDecimal("item_price")); // استخدمنا الـ Alias من הـ SQL
        return bean;
    }
}