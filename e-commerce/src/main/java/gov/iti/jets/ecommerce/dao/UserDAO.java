package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User insert(EntityManager em, User user);

    List<User> findAll(EntityManager em);

    Optional<User> findById(EntityManager em,int userId);

    Optional<User> findByEmail(EntityManager em,String email);


    List<User> findByRole(EntityManager em,UserRole role);

    List<User> searchByName(EntityManager em,String fullName);

    boolean update(EntityManager em,User user);

    boolean updateCreditBalance(EntityManager em,int userId, BigDecimal newBalance);

    boolean delete(EntityManager em,int userId);

    boolean existsByEmail(EntityManager em,String email);
}