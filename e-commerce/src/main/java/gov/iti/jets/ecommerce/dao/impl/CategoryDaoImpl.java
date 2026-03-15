package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CategoryDAO;
import gov.iti.jets.ecommerce.entity.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CategoryDaoImpl implements CategoryDAO {

    private final DataSource dataSource;

    public CategoryDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO categories (name, description) VALUES (?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM categories";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM categories WHERE category_id = ?";

    private static final String UPDATE_SQL =
            "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM categories WHERE category_id = ?";

    private static final String EXISTS_BY_NAME_SQL =
            "SELECT COUNT(*) FROM categories WHERE name = ?";

    @Override
    public Category insert(Category category) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save category: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setCategoryId(rs.getInt(1));
                }
            }
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(mapRowToCategory(rs));
            }
            return categories;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCategory(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Category category) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getCategoryId());

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
            // ملاحظة: لو حاولت تمسح قسم مربوط بمنتجات، الـ Database هترمي Exception بسبب ON DELETE RESTRICT
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_NAME_SQL)) {

            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Category mapRowToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
    }
}