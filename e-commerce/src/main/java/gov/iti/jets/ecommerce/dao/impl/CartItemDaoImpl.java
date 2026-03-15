package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartItemDAO;
import gov.iti.jets.ecommerce.entity.*;

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

    private static final String SELECT_BASE_SQL =
            "SELECT ci.*, p.*, c.name AS category_name, c.description AS category_description, " +
            "ct.user_id, ct.created_at AS cart_created_at " +
            "FROM cart_items ci " +
            "JOIN products p ON ci.product_id = p.product_id " +
            "JOIN categories c ON p.category_id = c.category_id " +
            "JOIN carts ct ON ci.cart_id = ct.cart_id";

    private static final String SELECT_BY_CART_ID_SQL = SELECT_BASE_SQL + " WHERE ci.cart_id = ?";

    private static final String SELECT_BY_ID_SQL = SELECT_BASE_SQL + " WHERE ci.cart_item_id = ?";

    private static final String UPDATE_QUANTITY_SQL =
            "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM cart_items WHERE cart_item_id = ?";

    private static final String DELETE_BY_CART_ID_SQL =
            "DELETE FROM cart_items WHERE cart_id = ?";

    @Override
    public CartItem insert(CartItem cartItem) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, cartItem.getCart().getCartId());
            statement.setInt(2, cartItem.getProduct().getProductId());
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
    public List<CartItem> findByCartId(int cartId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_CART_ID_SQL)) {

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
    public Optional<CartItem> findById(int cartItemId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, cartItemId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCartItem(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateQuantity(int cartItemId, int quantity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUANTITY_SQL)) {

            statement.setInt(1, quantity);
            statement.setInt(2, cartItemId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int cartItemId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, cartItemId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteByCartId(int cartId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_CART_ID_SQL)) {

            statement.setInt(1, cartId);
            return statement.executeUpdate() >= 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static CartItem mapRowToCartItem(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setQuantity(rs.getInt("quantity"));

        // 1. Map Product & Category
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setImageUrl(rs.getString("image_url"));
        
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));
        category.setDescription(rs.getString("category_description"));
        product.setCategory(category);
        
        Timestamp pCreated = rs.getTimestamp("p.created_at");
        if (pCreated != null) product.setCreatedAt(pCreated.toLocalDateTime());
        cartItem.setProduct(product);

        // 2. Map Cart (With minimal User ID reference)
        Cart cart = new Cart();
        cart.setCartId(rs.getInt("cart_id"));
        Timestamp cCreated = rs.getTimestamp("cart_created_at");
        if (cCreated != null) cart.setCreatedAt(cCreated.toLocalDateTime());        
        
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        cart.setUser(user);
        
        cartItem.setCart(cart);

        return cartItem;
    }
}