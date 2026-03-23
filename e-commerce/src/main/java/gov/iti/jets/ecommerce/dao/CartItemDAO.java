package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.CartItem;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface CartItemDAO {

    CartItem insert(EntityManager em, CartItem cartItem);

    List<CartItem> findByCartId(EntityManager em,int cartId);

    Optional<CartItem> findById(EntityManager em,int cartItemId);

    Optional<CartItem> findByCartIdAndProductId(EntityManager em,int cartId,int productId);

    boolean updateQuantity(EntityManager em,int cartItemId, int quantity);
    boolean updateQuantityByCartAndProductId(EntityManager em, int cartId,int productId, int quantity);

    boolean delete(EntityManager em,int cartItemId);

    boolean deleteByCartId(EntityManager em,int cartId);

    boolean deleteByCartIdAndProductId(EntityManager em, int cartId,int productId);
}