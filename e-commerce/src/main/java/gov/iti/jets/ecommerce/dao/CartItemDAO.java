package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemDAO {

    CartItem insert(CartItem cartItem);

    List<CartItem> findByCartId(int cartId);

    Optional<CartItem> findById(int cartItemId);

    boolean updateQuantity(int cartItemId, int quantity);

    boolean delete(int cartItemId);

    boolean deleteByCartId(int cartId);
}