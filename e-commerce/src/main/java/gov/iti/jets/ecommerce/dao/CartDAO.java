package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Cart;
import java.util.List;
import java.util.Optional;

public interface CartDAO {

    Cart insert(Cart cart);

    List<Cart> findAll();

    Optional<Cart> findById(int id);

    Optional<Cart> findByUserId(int userId);

    boolean delete(int id);
}