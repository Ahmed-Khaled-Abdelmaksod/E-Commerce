package gov.iti.jets.ecommerce.service.impl;

import gov.iti.jets.ecommerce.dao.UserDAO;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserBean registerUser(UserBean bean, String password) {
        User user = mapToEntity(bean);
        user.setPasswordHash(password); // المفروض هنا يتم عمل Hashing للباسورد
        User savedUser = userDAO.insert(user);
        return mapToBean(savedUser);
    }

    @Override
    public Optional<UserBean> getUserById(int id) {
        return userDAO.findById(id).map(this::mapToBean);
    }

    @Override
    public Optional<UserBean> getUserByEmail(String email) {
        return userDAO.findByEmail(email).map(this::mapToBean);
    }

    @Override
    public List<UserBean> getAllUsers() {
        return userDAO.findAll().stream()
                .map(this::mapToBean)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateUserDetails(UserBean bean) {
        Optional<User> existingUser = userDAO.findById(bean.getUserId());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFullName(bean.getFullName());
            user.setAddress(bean.getAddress());
            user.setPhone(bean.getPhone());
            return userDAO.update(user);
        }
        return false;
    }

    @Override
    public boolean depositCredit(int userId, BigDecimal amount) {
        Optional<User> user = userDAO.findById(userId);
        if (user.isPresent()) {
            BigDecimal newBalance = user.get().getCreditBalance().add(amount);
            return userDAO.updateCreditBalance(userId, newBalance);
        }
        return false;
    }

    @Override
    public boolean removeUser(int userId) {
        return userDAO.delete(userId);
    }

    // --- Helper Methods for Mapping ---
    
    private UserBean mapToBean(User entity) {
        UserBean bean = new UserBean();
        bean.setUserId(entity.getUserId());
        bean.setFullName(entity.getFullName());
        bean.setEmail(entity.getEmail());
        bean.setPhone(entity.getPhone());
        bean.setBirthday(entity.getBirthday());
        bean.setAddress(entity.getAddress());
        bean.setRole(entity.getRole());
        bean.setCreditBalance(entity.getCreditBalance());
        return bean;
    }

    private User mapToEntity(UserBean bean) {
        User entity = new User();
        entity.setUserId(bean.getUserId());
        entity.setFullName(bean.getFullName());
        entity.setEmail(bean.getEmail());
        entity.setPhone(bean.getPhone());
        entity.setBirthday(bean.getBirthday());
        entity.setAddress(bean.getAddress());
        entity.setRole(bean.getRole());
        entity.setCreditBalance(bean.getCreditBalance());
        return entity;
    }
}