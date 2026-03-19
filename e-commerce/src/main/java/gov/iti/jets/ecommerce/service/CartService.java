package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.impl.CartDaoImpl;
import gov.iti.jets.ecommerce.dao.impl.UserDaoImpl;
import gov.iti.jets.ecommerce.entity.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class CartService {
    static private final CartService instance;

    static {
        instance = new CartService();
    }

    private CartService() {

    }

    public int getUserCart(int userId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<Cart> cart = CartDaoImpl.getInstance().findCartByUserId(em,userId);
            if(cart.isPresent()) {
                return  cart.get().getCartId();
            }else {
                return -1;
            }
        }
    }


    public static CartService getInstance() {
        return instance;
    }
}
