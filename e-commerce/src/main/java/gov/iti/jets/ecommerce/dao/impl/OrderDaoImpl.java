package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.OrderDAO;
import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.OrderStatus;
import gov.iti.jets.ecommerce.enums.UserRole;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class OrderDaoImpl implements OrderDAO {

    private final DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, ?)";

    // التعديل هنا: u.* لجلب كل بيانات المستخدم
    private static final String SELECT_BASE_SQL =
            "SELECT o.*, u.* " + 
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.user_id";

    private static final String SELECT_ALL_SQL = SELECT_BASE_SQL;

    private static final String SELECT_BY_ID_SQL = SELECT_BASE_SQL + " WHERE o.order_id = ?";

    private static final String SELECT_BY_USER_ID_SQL = SELECT_BASE_SQL + " WHERE o.user_id = ?";

    private static final String SELECT_BY_STATUS_SQL = SELECT_BASE_SQL + " WHERE o.status = ?";

    private static final String UPDATE_STATUS_SQL =
            "UPDATE orders SET status = ? WHERE order_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM orders WHERE order_id = ?";

    @Override
    public Order insert(Order order) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.getUser().getUserId());
            statement.setBigDecimal(2, order.getTotalPrice());
            statement.setString(3, order.getStatus().name().toLowerCase());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save order: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setOrderId(rs.getInt(1));
                }
            }
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(mapRowToOrder(rs));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToOrder(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findByUserId(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_SQL)) {

            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_STATUS_SQL)) {

            statement.setString(1, status.name().toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStatus(int orderId, OrderStatus status) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS_SQL)) {

            statement.setString(1, status.name().toLowerCase());
            statement.setInt(2, orderId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Order mapRowToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setTotalPrice(rs.getBigDecimal("total_price"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status").toUpperCase()));
        
        Timestamp orderDateTs = rs.getTimestamp("order_date");
        if (orderDateTs != null) {
            order.setOrderDate(orderDateTs.toLocalDateTime());
        }

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));
        
        Date birthdayDate = rs.getDate("birthday");
        if (birthdayDate != null) {
            user.setBirthday(birthdayDate.toLocalDate());
        }
        
        user.setAddress(rs.getString("address"));
        user.setRole(UserRole.valueOf(rs.getString("role").toUpperCase()));
        user.setCreditBalance(rs.getBigDecimal("credit_balance"));
        
        Timestamp userCreatedTs = rs.getTimestamp("created_at");
        if (userCreatedTs != null) {
            user.setCreatedAt(userCreatedTs.toLocalDateTime());
        }
        
        order.setUser(user);

        return order;
    }
}