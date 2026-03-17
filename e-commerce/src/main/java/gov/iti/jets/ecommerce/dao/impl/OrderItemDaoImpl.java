package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.OrderItemDAO;
import gov.iti.jets.ecommerce.entity.*;
import gov.iti.jets.ecommerce.enums.OrderStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OrderItemDaoImpl implements OrderItemDAO {

    private final DataSource dataSource;

    public OrderItemDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

    private static final String SELECT_BASE_SQL =
            "SELECT oi.*, p.name, p.description, p.price AS p_price, p.stock_quantity, p.image_url, p.category_id, p.created_at AS p_created_at, " +
            "o.total_price, o.status, o.order_date, o.user_id " +
            "FROM order_items oi " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "JOIN orders o ON oi.order_id = o.order_id";

    private static final String SELECT_ALL_SQL = SELECT_BASE_SQL;
    private static final String SELECT_BY_ID_SQL = SELECT_BASE_SQL + " WHERE oi.order_item_id = ?";
    private static final String SELECT_BY_ORDER_ID_SQL = SELECT_BASE_SQL + " WHERE oi.order_id = ?";
    private static final String UPDATE_SQL = 
            "UPDATE order_items SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
    private static final String DELETE_SQL = "DELETE FROM order_items WHERE order_item_id = ?";

    @Override
    public OrderItem insert(OrderItem orderItem) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, orderItem.getOrder().getOrderId());
            statement.setInt(2, orderItem.getProduct().getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setBigDecimal(4, orderItem.getPrice());

            if (statement.executeUpdate() == 0) throw new RuntimeException("Couldn't save order item");
            
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) orderItem.setOrderItemId(rs.getInt(1));
            }
            return orderItem;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<OrderItem> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {
            List<OrderItem> items = new ArrayList<>();
            while (rs.next()) items.add(mapRowToOrderItem(rs));
            return items;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<OrderItem> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToOrderItem(rs)) : Optional.empty();
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public List<OrderItem> findByOrderId(int orderId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ORDER_ID_SQL)) {
            statement.setInt(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {
                List<OrderItem> items = new ArrayList<>();
                while (rs.next()) items.add(mapRowToOrderItem(rs));
                return items;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean update(OrderItem orderItem) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, orderItem.getOrder().getOrderId());
            statement.setInt(2, orderItem.getProduct().getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setBigDecimal(4, orderItem.getPrice());
            statement.setInt(5, orderItem.getOrderItemId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private static OrderItem mapRowToOrderItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setOrderItemId(rs.getInt("order_item_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getBigDecimal("price"));

        // Product (Full Attributes)
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("p_price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setImageUrl(rs.getString("image_url"));
        Timestamp pCreated = rs.getTimestamp("p_created_at");
        if (pCreated != null) product.setCreatedAt(Timestamp.valueOf(pCreated.toLocalDateTime()));

        // Category (SET ID ONLY)
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        product.setCategory(category);
        item.setProduct(product);

        // Order (Basic Attributes)
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setTotalPrice(rs.getBigDecimal("total_price"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
        Timestamp oDate = rs.getTimestamp("order_date");
        if (oDate != null) order.setOrderDate(oDate.toLocalDateTime());

        // User (SET ID ONLY)
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        order.setUser(user);
        item.setOrder(order);

        return item;
    }
}