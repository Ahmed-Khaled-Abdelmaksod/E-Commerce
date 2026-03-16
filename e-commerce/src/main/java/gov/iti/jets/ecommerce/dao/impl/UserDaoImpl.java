package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.UserDAO;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDaoImpl implements UserDAO {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // --- SQL Queries ---
    private static final String INSERT_SQL =
            "INSERT INTO users (full_name, email, password_hash, phone, birthday, address, role, credit_balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM users";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM users WHERE user_id = ?";

    private static final String SELECT_BY_EMAIL_SQL =
            "SELECT * FROM users WHERE email = ?";

    private static final String SELECT_BY_PHONE_SQL =
            "SELECT * FROM users WHERE phone = ?";

    private static final String SELECT_BY_ROLE_SQL =
            "SELECT * FROM users WHERE role = ?";

    private static final String SEARCH_BY_NAME_SQL =
            "SELECT * FROM users WHERE full_name LIKE ?";

    private static final String UPDATE_SQL =
            "UPDATE users SET full_name=?, email=?, password_hash=?, phone=?, birthday=?, address=?, role=?, credit_balance=? WHERE user_id=?";

    private static final String UPDATE_BALANCE_SQL =
            "UPDATE users SET credit_balance = ? WHERE user_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM users WHERE user_id = ?";

    private static final String EXISTS_BY_EMAIL_SQL =
            "SELECT COUNT(*) FROM users WHERE email = ?";

    @Override
    public User insert(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getPhone());
            statement.setObject(5, user.getBirthday());
            statement.setString(6, user.getAddress());
            // Store role in lowercase to match MySQL ENUM('admin', 'customer')
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
    public Optional<User> findById(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setInt(1, userId);
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
    public Optional<User> findByPhone(String phone) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PHONE_SQL)) {

            statement.setString(1, phone);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(mapRowToUser(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findByRole(UserRole role) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ROLE_SQL)) {

            // Search using lowercase role name
            statement.setString(1, role.name().toLowerCase());
            try (ResultSet rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> searchByName(String fullName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_BY_NAME_SQL)) {

            statement.setString(1, "%" + fullName + "%");
            try (ResultSet rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
                return users;
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
            statement.setObject(5, user.getBirthday());
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
    public boolean updateCreditBalance(int userId, BigDecimal newBalance) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BALANCE_SQL)) {

            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, userId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_SQL)) {

            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // --- Helper Methods ---

    private static User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));

        Date birthday = rs.getDate("birthday");
        if (birthday != null) {
            user.setBirthday(birthday.toLocalDate());
        }

        user.setAddress(rs.getString("address"));
        
        // Convert DB lowercase role to Java Uppercase Enum constant
        String roleFromDb = rs.getString("role");
        if (roleFromDb != null) {
            user.setRole(UserRole.valueOf(roleFromDb.toUpperCase()));
        }

        user.setCreditBalance(rs.getBigDecimal("credit_balance"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }

        return user;
    }
}