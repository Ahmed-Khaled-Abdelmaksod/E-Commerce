package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User insert(User user);

    List<User> findAll();

    Optional<User> findById(int userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findByRole(UserRole role);

    List<User> searchByName(String fullName);

    boolean update(User user);

    boolean updateCreditBalance(int userId, BigDecimal newBalance);

    boolean delete(int userId);

    boolean existsByEmail(String email);
}