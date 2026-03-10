package gov.iti.jets.ecommerce.test;

import gov.iti.jets.ecommerce.config.DataSourceConfig;
import gov.iti.jets.ecommerce.dao.*;
import gov.iti.jets.ecommerce.dao.impl.*;
import gov.iti.jets.ecommerce.entity.*;
import gov.iti.jets.ecommerce.enums.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class DaoAdvancedTest {

    private static final Logger logger =
            LoggerFactory.getLogger(DaoAdvancedTest.class);

    private static UserDAO userDAO;
    private static ProductDAO productDAO;
    private static CategorieDAO categorieDAO;
    private static CartDAO cartDAO;
    private static CartItemDAO cartItemDAO;
    private static OrderDAO orderDAO;
    private static OrderItemDAO orderItemDAO;

    public static void main(String[] args) {

        logger.info("========== DAO ADVANCED TEST START ==========");

        DataSource ds = DataSourceConfig.getDataSource();

        userDAO = new UserDaoImpl(ds);
        productDAO = new ProductDaoImpl(ds);
        categorieDAO = new CategorieDaoImpl(ds);
        cartDAO = new CartDaoImpl(ds);
        cartItemDAO = new CartItemDaoImpl(ds);
        orderDAO = new OrderDaoImpl(ds);
        orderItemDAO = new OrderItemDaoImpl(ds);

        testEnums();
        testUserDAO();
        testCategoryDAO();
        testProductDAO();
        testCartDAO();
        testCartItemDAO();
        testOrderDAO();
        testOrderItemDAO();

        logger.info("========== DAO ADVANCED TEST FINISHED ==========");
    }

    // ================= ENUM TEST =================

    private static void testEnums() {

        logger.info("Testing Enums...");

        for (UserRole role : UserRole.values()) {
            logger.info("UserRole available: {}", role);
        }

        for (OrderStatus status : OrderStatus.values()) {
            logger.info("OrderStatus available: {}", status);
        }

        logger.info("Enum test completed");
    }

    // ================= USER =================

    private static void testUserDAO() {

        logger.info("----- USER DAO TEST -----");

        long start = System.currentTimeMillis();

        User user = new User();
        user.setFullName("DAO Logging User");
        user.setEmail("logging@test.com");
        user.setPasswordHash("123456");
        user.setPhone("01000000000");
        user.setAddress("Cairo");
        user.setRole(UserRole.CUSTOMER);
        user.setCreditBalance(new BigDecimal("150"));

        user = userDAO.insert(user);
        assertLog(user.getUserId() > 0, "Insert User");

        Optional<User> found = userDAO.findById(user.getUserId());
        assertLog(found.isPresent(), "Find User By ID");

        Optional<User> byEmail = userDAO.findByEmail(user.getEmail());
        assertLog(byEmail.isPresent(), "Find User By Email");

        List<User> users = userDAO.findAll();
        logger.info("Total users in DB: {}", users.size());

        user.setFullName("Updated Logging User");
        assertLog(userDAO.update(user), "Update User");

        assertLog(userDAO.delete(user.getUserId()), "Delete User");

        long end = System.currentTimeMillis();
        logger.info("UserDAO Test finished in {} ms", (end - start));
    }

    // ================= CATEGORY =================

    private static void testCategoryDAO() {

        logger.info("----- CATEGORY DAO TEST -----");

        Categorie c = new Categorie();
        c.setName("Logging Category");
        c.setDescription("Category Test");

        c = categorieDAO.insert(c);
        assertLog(c.getCategoryId() > 0, "Insert Category");

        assertLog(categorieDAO.findById(c.getCategoryId()).isPresent(),
                "Find Category By ID");

        logger.info("All categories count: {}", categorieDAO.findAll().size());

        c.setName("Updated Category");
        assertLog(categorieDAO.update(c), "Update Category");

        assertLog(categorieDAO.delete(c.getCategoryId()), "Delete Category");
    }

    // ================= PRODUCT =================

    private static void testProductDAO() {

        logger.info("----- PRODUCT DAO TEST -----");

        Product p = new Product();
        p.setName("Logging Product");
        p.setDescription("Test product logging");
        p.setPrice(new BigDecimal("25"));
        p.setStockQuantity(10);
        p.setImageUrl("img.png");
        p.setCategoryId(1);

        p = productDAO.insert(p);
        assertLog(p.getProductId() > 0, "Insert Product");

        assertLog(productDAO.findById(p.getProductId()).isPresent(),
                "Find Product By ID");

        logger.info("Products in DB: {}", productDAO.findAll().size());

        logger.info("Products in category 1: {}",
                productDAO.findByCategory(1).size());

        p.setPrice(new BigDecimal("30"));
        assertLog(productDAO.update(p), "Update Product");

        assertLog(productDAO.delete(p.getProductId()), "Delete Product");
    }

    // ================= CART =================

    private static void testCartDAO() {

        logger.info("----- CART DAO TEST -----");

        Cart cart = new Cart();
        cart.setUserId(1);
        cart.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        cart = cartDAO.insert(cart);
        assertLog(cart.getCartId() > 0, "Insert Cart");

        assertLog(cartDAO.findById(cart.getCartId()).isPresent(),
                "Find Cart By ID");

        assertLog(cartDAO.findByUserId(1).isPresent(),
                "Find Cart By User");

        logger.info("Total carts: {}", cartDAO.findAll().size());

        assertLog(cartDAO.delete(cart.getCartId()), "Delete Cart");
    }

    // ================= CART ITEM =================

    private static void testCartItemDAO() {

        logger.info("----- CART ITEM DAO TEST -----");

        CartItem item = new CartItem();
        item.setCartId(1);
        item.setProductId(1);
        item.setQuantity(2);

        item = cartItemDAO.insert(item);
        assertLog(item.getCartItemId() > 0, "Insert CartItem");

        assertLog(cartItemDAO.findById(item.getCartItemId()).isPresent(),
                "Find CartItem By ID");

        logger.info("Cart items in cart 1: {}",
                cartItemDAO.findByCartId(1).size());

        item.setQuantity(5);
        assertLog(cartItemDAO.update(item), "Update CartItem");

        assertLog(cartItemDAO.delete(item.getCartItemId()), "Delete CartItem");
    }

    // ================= ORDER =================

    private static void testOrderDAO() {

        logger.info("----- ORDER DAO TEST -----");

        Order order = new Order();
        order.setUserId(1);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setTotalPrice(new BigDecimal("120"));
        order.setStatus(OrderStatus.PENDING);

        order = orderDAO.insert(order);
        assertLog(order.getOrderId() > 0, "Insert Order");

        assertLog(orderDAO.findById(order.getOrderId()).isPresent(),
                "Find Order By ID");

        logger.info("Orders for user 1: {}",
                orderDAO.findByUserId(1).size());

        order.setStatus(OrderStatus.COMPLETED);
        assertLog(orderDAO.update(order), "Update Order");

        assertLog(orderDAO.delete(order.getOrderId()), "Delete Order");
    }

    // ================= ORDER ITEM =================

    private static void testOrderItemDAO() {

        logger.info("----- ORDER ITEM DAO TEST -----");

        OrderItem item = new OrderItem();
        item.setOrderId(1);
        item.setProductId(1);
        item.setQuantity(3);
        item.setPrice(new BigDecimal("15"));

        item = orderItemDAO.insert(item);
        assertLog(item.getOrderItemId() > 0, "Insert OrderItem");

        assertLog(orderItemDAO.findById(item.getOrderItemId()).isPresent(),
                "Find OrderItem By ID");

        logger.info("Order items for order 1: {}",
                orderItemDAO.findByOrderId(1).size());

        item.setQuantity(5);
        assertLog(orderItemDAO.update(item), "Update OrderItem");

        assertLog(orderItemDAO.delete(item.getOrderItemId()),
                "Delete OrderItem");
    }

    // ================= LOGGER ASSERT =================

    private static void assertLog(boolean condition, String testName) {

        if (condition) {
            logger.info("✅ {} PASSED", testName);
        } else {
            logger.error("❌ {} FAILED", testName);
        }
    }
}