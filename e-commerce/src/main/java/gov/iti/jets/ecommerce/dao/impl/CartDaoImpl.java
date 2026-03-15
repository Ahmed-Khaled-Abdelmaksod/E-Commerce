package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartDAO;
import gov.iti.jets.ecommerce.entity.Cart;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CartDaoImpl implements CartDAO {

    private final DataSource dataSource;

    public CartDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO carts (user_id) VALUES (?)";

    private static final String SELECT_BASE_SQL =
            "SELECT c.*, u.* " +
            "FROM carts c " +
            "JOIN users u ON c.user_id = u.user_id";

    private static final String SELECT_ALL_SQL = SELECT_BASE_SQL;

    private static final String SELECT_BY_ID_SQL = SELECT_BASE_SQL + " WHERE c.cart_id = ?";

    private static final String SELECT_BY_USER_ID_SQL = SELECT_BASE_SQL + " WHERE c.user_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM carts WHERE cart_id = ?";

    @Override
    public Cart insert(Cart cart) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, cart.getUser().getUserId());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save cart: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    cart.setCartId(rs.getInt(1));
                }
            }
            return cart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Cart> carts = new ArrayList<>();
            while (rs.next()) {
                carts.add(mapRowToCart(rs));
            }
            return carts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cart> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCart(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cart> findByUserId(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_SQL)) {

            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCart(rs)) : Optional.empty();
            }
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

    private static Cart mapRowToCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(rs.getInt("cart_id"));
        
        Timestamp cartCreatedTs = rs.getTimestamp("created_at");
        if (cartCreatedTs != null) {
        }

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));

        Date bday = rs.getDate("birthday");
        if (bday != null) {
            user.setBirthday(bday.toLocalDate());
        }

        user.setAddress(rs.getString("address"));
        user.setRole(UserRole.valueOf(rs.getString("role").toUpperCase()));
        user.setCreditBalance(rs.getBigDecimal("credit_balance"));

        Timestamp userCreatedTs = rs.getTimestamp("u.created_at"); 
        if (userCreatedTs != null) {
            user.setCreatedAt(userCreatedTs.toLocalDateTime());
        }

        cart.setUser(user);
        return cart;
    }
}