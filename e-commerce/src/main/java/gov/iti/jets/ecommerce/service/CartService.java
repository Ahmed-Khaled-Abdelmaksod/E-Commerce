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
import jakarta.transaction.Transaction;

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
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Product product = new ProductDaoImpl().findById(em, productId).orElse(null);
            if (product == null) return CartItemStatus.NOTFOUND;

            CartItem existingItem = CartItemDaoImpl.getInstance()
                    .findByCartIdAndProductId(em, cartId, productId)
                    .orElse(null);

            int currentQtyInCart = (existingItem != null) ? existingItem.getQuantity() : 0;


            if (product.getStockQuantity() < currentQtyInCart + 1) {
                return CartItemStatus.LOWQUNATITY;
            }

            if (existingItem == null) {

                Cart cart = CartDaoImpl.getInstance().findById(em, cartId)
                        .orElseThrow(() -> new RuntimeException("Cart not found"));

                CartItem newItem = new CartItem();
                newItem.setQuantity(1);
                newItem.setProduct(product);
                newItem.setCart(cart);
                CartItemDaoImpl.getInstance().insert(em, newItem);
            } else {
                CartItemDaoImpl.getInstance().updateQuantity(em,
                        existingItem.getCartItemId(), currentQtyInCart + 1);
            }

            em.getTransaction().commit();
            return CartItemStatus.ADDED;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return CartItemStatus.ERROR;
        } finally {
            em.close();
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
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<CartItem> cartItems = CartItemDaoImpl.getInstance().findByCartId(em,cartId);
            List<CartItemDTO> cartItemDTOS = CartItemMapper.toDTOList(cartItems);
            tx.commit();
            return cartItemDTOS;
        }catch (Exception e) {
            if(tx.isActive()) {
                tx.rollback();
            }
        }finally {
            em.close();
        }
        return List.of();
    }

    public static CartService getInstance() {
        return instance;
    }
}
