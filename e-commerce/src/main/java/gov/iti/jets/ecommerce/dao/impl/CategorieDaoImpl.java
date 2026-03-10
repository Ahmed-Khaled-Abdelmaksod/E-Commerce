package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CategorieDAO;
import gov.iti.jets.ecommerce.entity.Categorie;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CategorieDaoImpl implements CategorieDAO {

    private final DataSource dataSource;

    public CategorieDaoImpl(DataSource dataSource) {
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

    @Override
    public Categorie insert(Categorie categorie) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, categorie.getName());
            statement.setString(2, categorie.getDescription());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save category: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    categorie.setCategoryId(rs.getInt(1));
                }
            }

            return categorie;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Categorie> findAll() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<Categorie> categories = new ArrayList<>();

            while (rs.next()) {
                categories.add(mapRowToCategorie(rs));
            }

            return categories;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Categorie> findById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToCategorie(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Categorie categorie) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, categorie.getName());
            statement.setString(2, categorie.getDescription());
            statement.setInt(3, categorie.getCategoryId());

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

    private static Categorie mapRowToCategorie(ResultSet rs) throws SQLException {

        Categorie categorie = new Categorie();

        categorie.setCategoryId(rs.getInt("category_id"));
        categorie.setName(rs.getString("name"));
        categorie.setDescription(rs.getString("description"));

        return categorie;
    }
}