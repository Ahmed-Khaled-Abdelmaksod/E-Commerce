package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.impl.UserDaoImpl;
import gov.iti.jets.ecommerce.entity.User;
import jakarta.persistence.EntityManager;
import java.util.Optional;

public class ProfileService {
    private static final ProfileService instance = new ProfileService();

    private ProfileService() {}

    public static ProfileService getInstance() {
        return instance;
    }


    public Optional<UserBean> updateProfile(UserBean userBean) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<User> existing = userDao.findById(em, userBean.getUserId());
            if (existing.isEmpty()) {
                return Optional.empty();
            }
            User user = existing.get();

            // Only update editable, non-sensitive fields
            if (userBean.getFullName() != null && !userBean.getFullName().isBlank()) {
                user.setFullName(userBean.getFullName());
            }
            user.setPhone(userBean.getPhone());
            user.setAddress(userBean.getAddress());
            user.setBirthday(userBean.getBirthday());

            em.getTransaction().begin();
            userDao.update(em, user);
            em.getTransaction().commit();

            // Return the refreshed bean
            UserBean updated = new UserBean();
            updated.setUserId(user.getUserId());
            updated.setFullName(user.getFullName());
            updated.setEmail(user.getEmail());
            updated.setPhone(user.getPhone());
            updated.setAddress(user.getAddress());
            updated.setBirthday(user.getBirthday());
            updated.setRole(user.getRole());
            updated.setCreditBalance(user.getCreditBalance());
            return Optional.of(updated);
        }
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
}