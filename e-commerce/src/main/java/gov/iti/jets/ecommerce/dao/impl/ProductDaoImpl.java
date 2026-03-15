package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.entity.Product;
import gov.iti.jets.ecommerce.entity.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ProductDaoImpl implements ProductDAO {

    private final DataSource dataSource;

    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO products (name, description, price, stock_quantity, image_url, category_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BASE_SQL = 
            "SELECT p.*, c.name AS category_name, c.description AS category_description " +
            "FROM products p " +
            "JOIN categories c ON p.category_id = c.category_id";

    private static final String SELECT_ALL_SQL = SELECT_BASE_SQL;

    private static final String SELECT_BY_ID_SQL = SELECT_BASE_SQL + " WHERE p.product_id = ?";

    private static final String SELECT_BY_CATEGORY_SQL = SELECT_BASE_SQL + " WHERE p.category_id = ?";

    private static final String SEARCH_BY_NAME_SQL = SELECT_BASE_SQL + " WHERE p.name LIKE ?";

    private static final String UPDATE_SQL =
            "UPDATE products SET name=?, description=?, price=?, stock_quantity=?, image_url=?, category_id=? WHERE product_id=?";

    private static final String DELETE_SQL =
            "DELETE FROM products WHERE product_id=?";

    @Override
    public Product insert(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setString(5, product.getImageUrl());
            statement.setInt(6, product.getCategory().getCategoryId());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save product: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setProductId(rs.getInt(1));
                }
            }
            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToProduct(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_CATEGORY_SQL)) {

            statement.setInt(1, categoryId);
            try (ResultSet rs = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> searchByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BY_NAME_SQL)) {

            statement.setString(1, "%" + name + "%");
            try (ResultSet rs = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setString(5, product.getImageUrl());
            statement.setInt(6, product.getCategory().getCategoryId());
            statement.setInt(7, product.getProductId());

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

    private static Product mapRowToProduct(ResultSet rs) throws SQLException {
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

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            product.setCreatedAt(createdAt.toLocalDateTime());
        }

        return product;
    }
}