package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartItemDAO;
import gov.iti.jets.ecommerce.entity.CartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CartItemDaoImpl implements CartItemDAO {

    private final DataSource dataSource;

    public CartItemDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM cart_items";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM cart_items WHERE cart_item_id = ?";

    private static final String SELECT_BY_CART_SQL =
            "SELECT * FROM cart_items WHERE cart_id = ?";

    private static final String UPDATE_SQL =
            "UPDATE cart_items SET cart_id = ?, product_id = ?, quantity = ? WHERE cart_item_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM cart_items WHERE cart_item_id = ?";

    @Override
    public CartItem insert(CartItem cartItem) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, cartItem.getCartId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save cart item: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    cartItem.setCartItemId(rs.getInt(1));
                }
            }

            return cartItem;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CartItem> findAll() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<CartItem> items = new ArrayList<>();

            while (rs.next()) {
                items.add(mapRowToCartItem(rs));
            }

            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartItem> findById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCartItem(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CartItem> findByCartId(int cartId) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_CART_SQL)) {

            statement.setInt(1, cartId);

            try (ResultSet rs = statement.executeQuery()) {

                List<CartItem> items = new ArrayList<>();

                while (rs.next()) {
                    items.add(mapRowToCartItem(rs));
                }

                return items;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CartItem cartItem) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setInt(1, cartItem.getCartId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            statement.setInt(4, cartItem.getCartItemId());

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

    private static CartItem mapRowToCartItem(ResultSet rs) throws SQLException {

        CartItem item = new CartItem();

        item.setCartItemId(rs.getInt("cart_item_id"));
        item.setCartId(rs.getInt("cart_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));

        return item;
    }
}