package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.UserDAO;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDaoImpl implements UserDAO {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_SQL =
            "INSERT INTO users (full_name,email,password_hash,phone,birthday,address,role,credit_balance) VALUES (?,?,?,?,?,?,?,?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM users";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM users WHERE user_id = ?";

    private static final String SELECT_BY_EMAIL_SQL =
            "SELECT * FROM users WHERE email = ?";

    private static final String UPDATE_SQL =
            "UPDATE users SET full_name=?,email=?,password_hash=?,phone=?,birthday=?,address=?,role=?,credit_balance=? WHERE user_id=?";

    private static final String DELETE_SQL =
            "DELETE FROM users WHERE user_id = ?";

    @Override
    public User insert(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getPhone());
            statement.setDate(5, Date.valueOf(user.getBirthday()));
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getRole().name().toLowerCase());
            statement.setBigDecimal(8, user.getCreditBalance());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Couldn't save user: NO ROWS AFFECTED");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = statement.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(int id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToUser(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToUser(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getPhone());
            statement.setDate(5, Date.valueOf(user.getBirthday()));
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getRole().name().toLowerCase());
            statement.setBigDecimal(8, user.getCreditBalance());
            statement.setInt(9, user.getUserId());

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

    private static User mapRowToUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        user.setAddress(rs.getString("address"));

        String roleValue = rs.getString("role");
        if (roleValue != null) {
            user.setRole(UserRole.valueOf(roleValue.toUpperCase()));
        }

        user.setCreditBalance(rs.getBigDecimal("credit_balance"));
        user.setCreatedAt(rs.getTimestamp("created_at"));

        return user;
    }
}