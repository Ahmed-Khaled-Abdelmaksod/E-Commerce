package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemDAO {

    CartItem insert(CartItem cartItem);

    List<CartItem> findAll();

    Optional<CartItem> findById(int id);

    List<CartItem> findByCartId(int cartId);

    boolean update(CartItem cartItem);

    boolean delete(int id);
}