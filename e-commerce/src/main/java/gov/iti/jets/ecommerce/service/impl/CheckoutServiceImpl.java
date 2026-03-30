package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.beans.checkout.CheckoutBean;
import gov.iti.jets.ecommerce.beans.checkout.CheckoutItemBean;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.entity.*;
import gov.iti.jets.ecommerce.enums.OrderStatus;
import gov.iti.jets.ecommerce.service.CheckoutService;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckoutServiceImpl implements CheckoutService {

    private final UserDAO userDAO;
    private final CartDAO cartDAO;
    private final CartItemDAO cartItemDAO;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;

    public CheckoutServiceImpl(UserDAO userDAO, CartDAO cartDAO, CartItemDAO cartItemDAO,
                               ProductDAO productDAO, OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
        this.userDAO = userDAO;
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public CheckoutBean getCheckoutDetails(int userId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {

            // 1. Fetch User's Cart first (If no cart or empty, return null)
            Optional<Cart> cartOpt = cartDAO.findCartByUserId(em, userId);
            if (cartOpt.isEmpty()) {
                return null; // No cart found
            }

            Cart cart = cartOpt.get();
            List<CartItem> cartItems = cartItemDAO.findByCartId(em, cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                return null; // Cart is empty
            }

            CheckoutBean bean = new CheckoutBean();

            // 2. Fetch User details
            Optional<User> userOpt = userDAO.findById(em, userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                bean.setCustomerName(user.getFullName());
                bean.setCustomerEmail(user.getEmail());
                bean.setCustomerPhone(user.getPhone());
                bean.setCustomerAddress(user.getAddress());
                bean.setUserCredit(user.getCreditBalance());
            }

            List<CheckoutItemBean> itemBeans = new ArrayList<>();
            BigDecimal subtotal = BigDecimal.ZERO;

            // 3. Map Cart Items to DTOs and calculate subtotal
            for (CartItem item : cartItems) {
                CheckoutItemBean itemBean = mapToCheckoutItemBean(item);
                itemBeans.add(itemBean);
                subtotal = subtotal.add(itemBean.getLineTotal());
            }

            // 4. Set Payment Summary
            bean.setOrderItems(itemBeans);
            bean.setSubtotal(subtotal);
            bean.setDeliveryFee(BigDecimal.ZERO); // Assuming Free Delivery
            bean.setTotal(subtotal.add(bean.getDeliveryFee()));

            return bean;
        }
    }

    @Override
    public boolean processCheckout(int userId) {
        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();

            // 1. Get User
            User user = userDAO.findById(em, userId).orElse(null);
            if (user == null) {
                em.getTransaction().rollback();
                return false;
            }

            // 2. Get User Cart
            Cart cart = cartDAO.findCartByUserId(em, userId).orElse(null);
            if (cart == null) {
                em.getTransaction().rollback();
                return false; // No cart found
            }

            // 3. Get Cart Items
            List<CartItem> cartItems = cartItemDAO.findByCartId(em, cart.getCartId());
            if (cartItems.isEmpty()) {
                em.getTransaction().rollback();
                return false; // Cart is empty
            }

            // Calculate total using current product prices
            BigDecimal calculatedTotal = BigDecimal.ZERO;
            for (CartItem item : cartItems) {
                BigDecimal itemPrice = item.getProduct().getPrice();
                BigDecimal itemTotal = itemPrice.multiply(new BigDecimal(item.getQuantity()));
                calculatedTotal = calculatedTotal.add(itemTotal);
            }

            // 4. Check User Credit Balance
            if (user.getCreditBalance().compareTo(calculatedTotal) < 0) {
                em.getTransaction().rollback();
                return false; // Insufficient funds
            }

            // 5. Check Availability and Update Stock
            for (CartItem item : cartItems) {
                Product product = item.getProduct();
                if (product.getStockQuantity() < item.getQuantity()) {
                    em.getTransaction().rollback();
                    return false; // Product is out of stock
                }
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                productDAO.update(em, product);
            }

            // 6. Deduct User Balance
            user.setCreditBalance(user.getCreditBalance().subtract(calculatedTotal));
            userDAO.update(em, user);

            // 7. Create New Order
            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setTotalPrice(calculatedTotal);
            newOrder.setStatus(OrderStatus.PAID);
            newOrder = orderDAO.insert(em, newOrder); // Save Order to DB

            // 8. Create Order Items from Cart Items
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(newOrder);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getPrice()); // Snapshot the price at checkout

                orderItemDAO.insert(em, orderItem); // Save OrderItem to DB
            }

            // 9. Clear the Cart Items
            cartItemDAO.deleteByCartId(em, cart.getCartId());

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public BigDecimal getUserBalanceAfterCheckout(int userId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<User> userOpt = userDAO.findById(em, userId);
            if (userOpt.isPresent()) {
                BigDecimal balance = userOpt.get().getCreditBalance();
                return balance != null ? balance : BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // --- Private Helper & Mapper Methods ---

    private CheckoutItemBean mapToCheckoutItemBean(CartItem itemEntity) {
        CheckoutItemBean bean = new CheckoutItemBean();
        bean.setProductName(itemEntity.getProduct().getName());
        bean.setImageUrl(itemEntity.getProduct().getImageUrl());
        bean.setQuantity(itemEntity.getQuantity());

        // Calculate line total: current product price * quantity
        BigDecimal currentPrice = itemEntity.getProduct().getPrice();
        BigDecimal lineTotal = currentPrice.multiply(new BigDecimal(itemEntity.getQuantity()));
        bean.setLineTotal(lineTotal);

        return bean;
    }
}