package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.UserBean;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserBean registerUser(UserBean userBean, String password);
    Optional<UserBean> getUserById(int id);
    Optional<UserBean> getUserByEmail(String email);
    List<UserBean> getAllUsers();
    boolean updateUserDetails(UserBean userBean);
    boolean depositCredit(int userId, BigDecimal amount);
    boolean removeUser(int userId);
}