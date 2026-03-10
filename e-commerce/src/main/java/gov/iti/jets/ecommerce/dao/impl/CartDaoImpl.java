package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartDAO;
import gov.iti.jets.ecommerce.entity.Cart;

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
            "INSERT INTO carts (user_id, created_at) VALUES (?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM carts";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM carts WHERE cart_id = ?";

    private static final String SELECT_BY_USER_SQL =
            "SELECT * FROM carts WHERE user_id = ?";

    private static final String UPDATE_SQL =
            "UPDATE carts SET user_id = ?, created_at = ? WHERE cart_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM carts WHERE cart_id = ?";

    @Override
    public Cart insert(Cart cart) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            if (cart.getCreatedAt() == null) {
                cart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            }

            statement.setInt(1, cart.getUserId());
            statement.setTimestamp(2, cart.getCreatedAt());

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
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_SQL)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCart(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Cart cart) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setInt(1, cart.getUserId());
            statement.setTimestamp(2, cart.getCreatedAt());
            statement.setInt(3, cart.getCartId());

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

    private static Cart mapRowToCart(ResultSet rs) throws SQLException {

        Cart cart = new Cart();

        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at"));

        return cart;
    }
}