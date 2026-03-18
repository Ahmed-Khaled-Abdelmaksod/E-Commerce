package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.UserDAO;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDaoImpl implements UserDAO {

    private static UserDaoImpl instance;
    static {
        instance = new UserDaoImpl();
    }

    public UserDaoImpl() {

    }


    @Override
    public User insert(EntityManager em, User user) {
        em.persist(user);
        return user;
    }

    @Override
    public List<User> findAll(EntityManager em) {
        return em.createQuery("from User u",User.class).getResultList();
    }

    @Override
    public Optional<User> findById(EntityManager em,int userId) {
        User user = em.find(User.class,userId);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(EntityManager em, String email) {
        List<User> result = em.createQuery(
                        "from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findFirst();
    }


    @Override
    public List<User> findByRole(EntityManager em,UserRole role) {
        return em.createQuery("from User u where u.role = :role",User.class)
                .setParameter("role",role).getResultList();
    }

    @Override
    public List<User> searchByName(EntityManager em,String fullName) {
        return em.createQuery("from User u where u.fullName = :fullName",User.class)
                .setParameter("fullName",fullName).getResultList();
    }

    @Override
    public boolean update(EntityManager em, User user) {
        if (user == null || user.getUserId() == null) {
            return false;
        }
        User merged = em.merge(user);
        return merged != null;
    }

    @Override
    public boolean updateCreditBalance(EntityManager em, int userId, BigDecimal newBalance) {
        int updated = em.createQuery(
                        "UPDATE User u SET u.creditBalance = :balance WHERE u.userId = :id")
                .setParameter("balance", newBalance)
                .setParameter("id", userId)
                .executeUpdate();
        return updated > 0;
    }
    @Override
    public boolean delete(EntityManager em, int userId) {
        int deletedCount = em.createQuery("DELETE FROM User u WHERE u.userId = :id")
                .setParameter("id", userId)
                .executeUpdate();
        return deletedCount > 0;
    }
    @Override
    public boolean existsByEmail(EntityManager em, String email) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }
}