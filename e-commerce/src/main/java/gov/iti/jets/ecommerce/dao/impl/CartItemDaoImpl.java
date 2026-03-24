package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CartItemDAO;
import gov.iti.jets.ecommerce.entity.*;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public final class CartItemDaoImpl implements CartItemDAO {
    static private final CartItemDaoImpl instance;

    static {
        instance = new CartItemDaoImpl();
    }

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
    public boolean updateQuantityByCartAndProductId(EntityManager em, int cartId,int productId, int quantity) {
        int updated = em.createQuery(
                        "UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.cart.cartId = :cartId and ci.product.productId =:productId")
                .setParameter("quantity", quantity)
                .setParameter("cartId",cartId)
                .setParameter("productId", productId)
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

    @Override
    public boolean deleteByCartIdAndProductId(EntityManager em, int cartId,int productId) {
        int deletedCount = em.createQuery(
                        "DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId and ci.product.productId = :productId")
                .setParameter("cartId", cartId)
                .setParameter("productId",productId)
                .executeUpdate();
        return deletedCount > 0;
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductId(EntityManager em,int cartId,int productId) {
        return em.createQuery(
                        "from CartItem ci where ci.cart.cartId = :cartId and ci.product.productId = :productId", CartItem.class)
                .setParameter("cartId", cartId)
                .setParameter("productId",productId)
                .getResultStream().findFirst();
    }

    public static CartItemDaoImpl getInstance() {
        return instance;
    }
}