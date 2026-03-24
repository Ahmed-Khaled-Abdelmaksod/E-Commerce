package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.impl.OrderDaoImpl;
import gov.iti.jets.ecommerce.dao.impl.UserDaoImpl;
import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.entity.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class ProfileService {
    private static final ProfileService instance = new ProfileService();

    private ProfileService() {}

    public static ProfileService getInstance() {
        return instance;
    }

    /**
     * Updates editable profile fields.
     * Returns an error message string if validation fails, or empty Optional if user not found.
     * On success returns the refreshed UserBean.
     * Result is wrapped in a ProfileResult record.
     */
    public ProfileResult updateProfile(UserBean userBean) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {

            // -- Phone uniqueness check --
            if (userBean.getPhone() != null && !userBean.getPhone().isBlank()) {
                List<User> allUsers = userDao.findAll(em);
                boolean phoneTaken = allUsers.stream()
                        .anyMatch(u -> userBean.getPhone().equals(u.getPhone())
                                && u.getUserId() != userBean.getUserId());
                if (phoneTaken) {
                    return ProfileResult.error("This phone number is already registered to another account.");
                }
            }

            Optional<User> existing = userDao.findById(em, userBean.getUserId());
            if (existing.isEmpty()) {
                return ProfileResult.error("User not found.");
            }
            User user = existing.get();

            if (userBean.getFullName() != null && !userBean.getFullName().isBlank()) {
                user.setFullName(userBean.getFullName());
            }
            user.setPhone(userBean.getPhone());
            user.setAddress(userBean.getAddress());
            user.setBirthday(userBean.getBirthday());

            em.getTransaction().begin();
            userDao.update(em, user);
            em.getTransaction().commit();

            UserBean updated = new UserBean();
            updated.setUserId(user.getUserId());
            updated.setFullName(user.getFullName());
            updated.setEmail(user.getEmail());
            updated.setPhone(user.getPhone());
            updated.setAddress(user.getAddress());
            updated.setBirthday(user.getBirthday());
            updated.setRole(user.getRole());
            updated.setCreditBalance(user.getCreditBalance());
            return ProfileResult.success(updated);
        }
    }

    /**
     * Returns all orders for a given userId, newest first.
     */
    public List<Order> getOrdersByUserId(int userId) {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<Order> orders = orderDao.findByUserId(em, userId);
            orders.sort((a, b) -> b.getOrderDate().compareTo(a.getOrderDate()));
            return orders;
        }
    }

    /**
     * Computes the remaining credit for a user:
     *   remaining = creditBalance − sum(totalPrice of PAID or COMPLETED orders)
     * Does NOT touch any cart code.
     */
    public java.math.BigDecimal getRemainingCredit(java.math.BigDecimal creditBalance, List<Order> orders) {
        java.math.BigDecimal spent = orders.stream()
                .filter(o -> o.getStatus() == gov.iti.jets.ecommerce.enums.OrderStatus.PAID
                          || o.getStatus() == gov.iti.jets.ecommerce.enums.OrderStatus.COMPLETED)
                .map(Order::getTotalPrice)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        java.math.BigDecimal remaining = creditBalance.subtract(spent);
        return remaining.compareTo(java.math.BigDecimal.ZERO) < 0 ? java.math.BigDecimal.ZERO : remaining;
    }

    /**
     * Finds a user by email for profile display or admin checks.
     */
    public Optional<UserBean> findUserByEmail(String email) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<User> user = userDao.findByEmail(em, email);
            return user.map(u -> {
                UserBean bean = new UserBean();
                bean.setUserId(u.getUserId());
                bean.setEmail(u.getEmail());
                bean.setRole(u.getRole());
                bean.setFullName(u.getFullName());
                bean.setAddress(u.getAddress());
                bean.setPhone(u.getPhone());
                bean.setBirthday(u.getBirthday());
                bean.setCreditBalance(u.getCreditBalance());
                return bean;
            });
        }
    }

    // ---- Simple result wrapper ------------------------------------------------
    public static class ProfileResult {
        private final UserBean user;
        private final String errorMessage;

        private ProfileResult(UserBean user, String errorMessage) {
            this.user = user;
            this.errorMessage = errorMessage;
        }

        public static ProfileResult success(UserBean user) { return new ProfileResult(user, null); }
        public static ProfileResult error(String msg)      { return new ProfileResult(null, msg); }

        public boolean isSuccess()       { return errorMessage == null; }
        public UserBean getUser()        { return user; }
        public String getErrorMessage()  { return errorMessage; }
    }
}