package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.SignUpBean;
import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.config.DataSourceConfig;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.impl.UserDaoImpl;
import gov.iti.jets.ecommerce.entity.User;
import gov.iti.jets.ecommerce.mappers.UserMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {
    private static final AuthService instance;
    static  {
        instance = new AuthService();
    }
    private AuthService() {

    }
    public Optional<UserBean> login(String email, String password) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<User> user = userDao.findByEmail(em,email);
            if(user.isPresent()) {
                User u = user.get();
                if(BCrypt.checkpw(password,u.getPasswordHash())) {
                    UserBean userBean = new UserBean();
                    userBean.setUserId(u.getUserId());
                    userBean.setEmail(u.getEmail());
                    userBean.setRole(u.getRole());
                    userBean.setAddress(u.getAddress());
                    userBean.setCreditBalance(u.getCreditBalance());
                    userBean.setBirthday(u.getBirthday());
                    userBean.setFullName(u.getFullName());
                    userBean.setPhone(u.getPhone());
                    return Optional.of(userBean);
                }
            }
            return Optional.empty();
        }
    }
    public String signUp(SignUpBean signUpBean) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            String validationMessage = validator(signUpBean);
            if(validationMessage != null) {
                return validationMessage;
            }
            User user = UserMapper.mapSignUpBeanToUser(signUpBean);
            userDao.insert(em,user);
            return null;
        }
    }
    public boolean isEmailExist(String email) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try(EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Optional<User> user = userDao.findByEmail(em,email);
            return user.isPresent();
        }
    }

    public static AuthService getInstance() {
        return instance;
    }
    private String validator(SignUpBean signUpBean) {
        if (signUpBean == null) {
            return "Form data is missing.";
        }

        // 1. Full Name Validation (Letters and spaces only)
        if (signUpBean.getFullName() == null || !signUpBean.getFullName().matches("^[A-Za-z\\s]+$")) {
            return "Full Name must contain only characters and spaces.";
        }

        // 2. Email Validation (Standard Email Regex)
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (signUpBean.getEmail() == null || !signUpBean.getEmail().matches(emailRegex)) {
            return "Please enter a valid email address.";
        }

//        // 3. Password Strength (Min 8 chars, 1 Upper, 1 Lower, 1 Number, 1 Special)
//        String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
//        if (signUpBean.getPassword() == null || !signUpBean.getPassword().matches(passRegex)) {
//            return "Password must be at least 8 characters and include uppercase, lowercase, number, and special character.";
//        }

        // 4. Phone Validation
        String phoneRegex = "^(\\+20|0)?1[0125]\\d{8}$";
        if (signUpBean.getPhone() == null || !signUpBean.getPhone().matches(phoneRegex)) {
            return "Please enter a valid mobile phone number.";
        }

        // 5. Birthday Validation (Age check - must be 13 or older)
        if (signUpBean.getBirthday() == null) {
            return "Birthday is required.";
        } else {
            java.time.LocalDate now = java.time.LocalDate.now();
            int age = java.time.Period.between(signUpBean.getBirthday(), now).getYears();
            if (signUpBean.getBirthday().isAfter(now)) {
                return "Birthday cannot be in the future.";
            }
            if (age < 13) {
                return "You must be at least 13 years old to register.";
            }
        }

        // 6. Address Validation (Simple non-empty check)
        if (signUpBean.getAddress() == null || signUpBean.getAddress().trim().isEmpty()) {
            return "Address cannot be empty.";
        }

        // If all checks pass, return null (meaning no error)
        return null;
    }
}
