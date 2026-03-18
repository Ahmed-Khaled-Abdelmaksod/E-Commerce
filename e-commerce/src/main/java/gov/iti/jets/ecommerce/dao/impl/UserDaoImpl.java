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

    private final DataSource dataSource;
    private static UserDaoImpl instance;
    static {
        instance = new UserDaoImpl();
    }

    public UserDaoImpl() {

    }


    @Override
    public User insert(EntityManager em, User user) {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();
            em.persist(user);
            entityTransaction.commit();
            return user;
        }catch (Exception e){
            if(entityTransaction.isActive()) entityTransaction.rollback();
            throw new RuntimeException("can't insert the user due to database error" ,e);
        }
    }

    @Override
    public List<User> findAll(EntityManager em) {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            return em.createQuery("from User u",User.class).getResultList();
        }catch (Exception e){
            if(entityTransaction.isActive()) entityTransaction.rollback();
            throw new RuntimeException("can't get users due to database error" ,e);
        }
    }

    @Override
    public Optional<User> findById(EntityManager em,int userId) {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();
            User user = em.find(User.class,userId);
            entityTransaction.commit();
            return Optional.ofNullable(user);
        }catch (Exception e){
            if(entityTransaction.isActive()) entityTransaction.rollback();
            throw new RuntimeException("can't user with id "+userId +" due to database error" ,e);
        }
    }

    @Override
    public Optional<User> findByEmail(EntityManager em,String email) {
        User user = em.createQuery("from User u where u.email = :email",User.class)
                .setParameter("email",email).getSingleResult();
            return Optional.ofNullable(user);
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
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean updateCreditBalance(EntityManager em, int userId, BigDecimal newBalance) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, userId);
            if (user == null) {
                return false;
            }
            user.setCreditBalance(newBalance);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        }
    }
    @Override
    public boolean delete(EntityManager em, int userId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int deletedCount = em.createQuery("DELETE FROM User u WHERE u.id = :id")
                    .setParameter("id", userId)
                    .executeUpdate();
            tx.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        }
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