package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.beans.productDetails.ProductDetailsBean;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.CartDAO;
import gov.iti.jets.ecommerce.dao.CartItemDAO;
import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.dao.UserDAO;
import gov.iti.jets.ecommerce.entity.Cart;
import gov.iti.jets.ecommerce.entity.CartItem;
import gov.iti.jets.ecommerce.entity.Product;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.enums.CartItemStatus;
import gov.iti.jets.ecommerce.mappers.ProductDetailsMapper;
import gov.iti.jets.ecommerce.service.ProductDetailsService;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDAO productDAO;
    private final CartDAO cartDAO;
    private final CartItemDAO cartItemDAO;
    private final UserDAO userDAO;

    // Dependency Injection via Constructor (Will be initialized in ServiceLocator)
    public ProductDetailsServiceImpl(ProductDAO productDAO, CartDAO cartDAO, 
                                     CartItemDAO cartItemDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.userDAO = userDAO;
    }

    @Override
    public ProductDetailsBean getProductDetailsById(int productId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<Product> productOpt = productDAO.findById(em, productId);
            
            if (productOpt.isPresent()) {
                // Utilizing the newly created ProductDetailsMapper
                return ProductDetailsMapper.toBean(productOpt.get());
            }
            
            return null;
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Service - getProductDetailsById: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CartItemStatus addProductToCart(int userId, int productId, int quantity) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();

            // 1. Check if product exists
            Optional<Product> productOpt = productDAO.findById(em, productId);
            if (!productOpt.isPresent()) {
                em.getTransaction().rollback();
                return CartItemStatus.NOTFOUND;
            }

            Product product = productOpt.get();

            // 2. Check if requested quantity exceeds stock
            if (product.getStockQuantity() < quantity) {
                em.getTransaction().rollback();
                return CartItemStatus.LOWQUNATITY;
            }

            // 3. Get or Create Cart for the user
            Optional<Cart> cartOpt = cartDAO.findCartByUserId(em, userId);
            Cart cart;
            
            if (cartOpt.isPresent()) {
                cart = cartOpt.get();
            } else {
                Optional<User> userOpt = userDAO.findById(em, userId);
                if (!userOpt.isPresent()) {
                    em.getTransaction().rollback();
                    return CartItemStatus.ERROR;
                }
                cart = new Cart();
                cart.setUser(userOpt.get());
                cartDAO.insert(em, cart);
            }

            // 4. Add new item or update existing item in cart
            Optional<CartItem> cartItemOpt = cartItemDAO.findByCartIdAndProductId(em, cart.getCartId(), productId);
            if (cartItemOpt.isPresent()) {
                CartItem existingItem = cartItemOpt.get();
                int newQuantity = existingItem.getQuantity() + quantity;
                
                // Verify that the new total quantity doesn't exceed the stock
                if (product.getStockQuantity() < newQuantity) {
                    em.getTransaction().rollback();
                    return CartItemStatus.LOWQUNATITY;
                }
                
                cartItemDAO.updateQuantity(em, existingItem.getCartItemId(), newQuantity);
            } else {
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(quantity);
                cartItemDAO.insert(em, newItem);
            }

            em.getTransaction().commit();
            return CartItemStatus.ADDED;
            
        } catch (Exception e) {
            System.err.println("Error adding product to cart: " + e.getMessage());
            e.printStackTrace();
            return CartItemStatus.ERROR;
        }
    }
}