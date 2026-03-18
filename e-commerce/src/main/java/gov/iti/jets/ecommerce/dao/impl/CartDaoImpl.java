package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartDAO;
import gov.iti.jets.ecommerce.entity.Cart;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CartDaoImpl implements CartDAO {

    private static CartDaoImpl instance;
    static {
        instance = new CartDaoImpl();
    }
    public CartDaoImpl() {

    }


    @Override
    public Cart insert(EntityManager em,Cart cart) {
        em.persist(cart);
        return cart;
    }

    @Override
    public List<Cart> findAll(EntityManager em) {
        return em.createQuery("from Cart c",Cart.class).getResultList();
    }

    @Override
    public Optional<Cart> findById(EntityManager em,int id) {
        Cart cart = em.find(Cart.class,id);
        return Optional.ofNullable(cart);
    }

    @Override
    public Optional<Cart> findByUserId(EntityManager em,int userId) {
        Cart cart = em.createQuery("from Cart c where c.user.userId = :userId",Cart.class)
                .setParameter("userId",userId).getSingleResult();
        return Optional.ofNullable(cart);
    }

    @Override
    public boolean delete(EntityManager em,int id) {
        int deletedCount = em.createQuery("DELETE FROM Cart c WHERE c.cartId = :id")
                .setParameter("id", id)
                .executeUpdate();
        return deletedCount > 0;
    }

    public static CartDaoImpl getInstance() {
        return instance;
    }
}