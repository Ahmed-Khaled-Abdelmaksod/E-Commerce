package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Cart;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface CartDAO {

    Cart insert(EntityManager em, Cart cart);

    List<Cart> findAll(EntityManager em);

    Optional<Cart> findById(EntityManager em,int id);

    Optional<Cart> findCartByUserId(EntityManager em,int userId);

    boolean delete(EntityManager em,int id);
}