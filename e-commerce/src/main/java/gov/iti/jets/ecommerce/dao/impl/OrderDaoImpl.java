package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.OrderDAO;
import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.enums.OrderStatus;

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
            "INSERT INTO orders (user_id, order_date, total_price, status) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM orders ORDER BY order_date DESC";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM orders WHERE order_id = ?";

    private static final String SELECT_BY_USER_SQL =
            "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

    private static final String UPDATE_SQL =
            "UPDATE orders SET user_id=?, order_date=?, total_price=?, status=? WHERE order_id=?";

    private static final String DELETE_SQL =
            "DELETE FROM orders WHERE order_id=?";

    @Override
    public Order insert(Order order) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            if (order.getOrderDate() == null) {
                order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            }

            statement.setInt(1, order.getUserId());
            statement.setTimestamp(2, order.getOrderDate());
            statement.setBigDecimal(3, order.getTotalPrice());
            statement.setString(4, order.getStatus().name().toLowerCase());

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
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_SQL)) {

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
    public boolean update(Order order) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setInt(1, order.getUserId());
            statement.setTimestamp(2, order.getOrderDate());
            statement.setBigDecimal(3, order.getTotalPrice());
            statement.setString(4, order.getStatus().name().toLowerCase());
            statement.setInt(5, order.getOrderId());

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
        order.setUserId(rs.getInt("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setTotalPrice(rs.getBigDecimal("total_price"));

        String statusValue = rs.getString("status");
        if (statusValue != null) {
            order.setStatus(OrderStatus.valueOf(statusValue.toUpperCase()));
        }

        return order;
    }
}