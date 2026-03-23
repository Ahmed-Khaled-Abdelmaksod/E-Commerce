package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.DTO.CartItemDTO;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.impl.CartDaoImpl;
import gov.iti.jets.ecommerce.dao.impl.CartItemDaoImpl;
import gov.iti.jets.ecommerce.dao.impl.ProductDaoImpl;
import gov.iti.jets.ecommerce.dao.impl.UserDaoImpl;
import gov.iti.jets.ecommerce.entity.Cart;
import gov.iti.jets.ecommerce.entity.CartItem;
import gov.iti.jets.ecommerce.entity.Product;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.CartItemStatus;
import gov.iti.jets.ecommerce.mappers.CartItemMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CartService {
    static private final CartService instance;

    static {
        instance = new CartService();
    }

    private CartService() {

    }
    public CartItemStatus addToCart(int cartId, int productId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();

                Product product = new ProductDaoImpl().findById(em, productId).orElse(null);
                if (product == null) {
                    tx.rollback();
                    return CartItemStatus.NOTFOUND;
                }

                CartItem existingItem = CartItemDaoImpl.getInstance()
                        .findByCartIdAndProductId(em, cartId, productId)
                        .orElse(null);

                int currentQtyInCart = (existingItem != null) ? existingItem.getQuantity() : 0;


                if (product.getStockQuantity() < currentQtyInCart + 1) {
                    tx.rollback();
                    return CartItemStatus.LOWQUNATITY;
                }

                Cart cart = CartDaoImpl.getInstance().findById(em, cartId)
                        .orElseThrow(() -> new RuntimeException("Cart not found"));

                User user = cart.getUser();
                BigDecimal userCredit = user != null && user.getCreditBalance() != null
                        ? user.getCreditBalance()
                        : BigDecimal.ZERO;

                BigDecimal cartTotal = BigDecimal.ZERO;
                List<CartItem> allItems = CartItemDaoImpl.getInstance().findByCartId(em, cartId);
                for (CartItem item : allItems) {
                    if (item.getProduct() != null && item.getProduct().getPrice() != null && item.getQuantity() != null) {
                        cartTotal = cartTotal.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    }
                }

                BigDecimal nextTotal = cartTotal.add(product.getPrice());
                if (nextTotal.compareTo(userCredit) > 0) {
                    tx.rollback();
                    return CartItemStatus.INSUFFICIENT_CREDIT;
                }

                if (existingItem == null) {
                    CartItem newItem = new CartItem();
                    newItem.setQuantity(1);
                    newItem.setProduct(product);
                    newItem.setCart(cart);
                    CartItemDaoImpl.getInstance().insert(em, newItem);
                } else {
                    CartItemDaoImpl.getInstance().updateQuantity(em,
                            existingItem.getCartItemId(), currentQtyInCart + 1);
                }

                tx.commit();
                return CartItemStatus.ADDED;

            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                return CartItemStatus.ERROR;
            }
        }
    }
    public boolean removeFromCart(int cartId, int productId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            boolean result = CartItemDaoImpl.getInstance().deleteByCartIdAndProductId(em, cartId, productId);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updateCartItem(int cartId, int productId, int quantity) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            boolean result = CartItemDaoImpl.getInstance().updateQuantityByCartAndProductId(em, cartId, productId, quantity);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean clearCart(int cartId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            boolean result = CartItemDaoImpl.getInstance().deleteByCartId(em, cartId);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    public int createCart(int userId) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            User user = UserDaoImpl.getInstance().findById(em, userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            Cart cart = new Cart();
            cart.setUser(user);

            Cart savedCart = CartDaoImpl.getInstance().insert(em, cart);

            em.getTransaction().commit();
            return savedCart.getCartId();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return -1;
        } finally {
            em.close();
        }
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
    public List<CartItemDTO> getCartItems(int cartId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<CartItem> cartItems = CartItemDaoImpl.getInstance().findByCartId(em,cartId);
            return CartItemMapper.toDTOList(cartItems);
        } catch (Exception e) {
            return List.of();
        }
    }

    public static CartService getInstance() {
        return instance;
    }
}
