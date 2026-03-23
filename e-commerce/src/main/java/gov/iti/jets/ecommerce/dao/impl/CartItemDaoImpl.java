package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartItemDAO;
import gov.iti.jets.ecommerce.entity.*;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class CartItemDaoImpl implements CartItemDAO {

    public CartItemDaoImpl() {
    }

    @Override
    public CartItem insert(EntityManager em, CartItem cartItem) {
        em.persist(cartItem);
        return cartItem;
    }

    @Override
    public List<CartItem> findByCartId(EntityManager em, int cartId) {
        return em.createQuery(
                        "from CartItem ci where ci.cart.cartId = :cartId", CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public Optional<CartItem> findById(EntityManager em, int cartItemId) {
        CartItem cartItem = em.find(CartItem.class,cartItemId);
        return Optional.ofNullable(cartItem);
    }

    @Override
    public boolean updateQuantity(EntityManager em, int cartItemId, int quantity) {
        int updated = em.createQuery(
                        "UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.cartItemId = :cartItemId")
                .setParameter("quantity", quantity)
                .setParameter("cartItemId", cartItemId)
                .executeUpdate();
        return updated > 0;
    }

    @Override
    public boolean delete(EntityManager em, int cartItemId) {
        int deletedCount = em.createQuery("DELETE FROM CartItem ci WHERE ci.cartItemId = :cartItemId")
                .setParameter("cartItemId", cartItemId)
                .executeUpdate();
        return deletedCount > 0;
    }

    @Override
    public boolean deleteByCartId(EntityManager em, int cartId) {
        int deletedCount = em.createQuery(
                        "DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();
        return deletedCount > 0;
    }
}