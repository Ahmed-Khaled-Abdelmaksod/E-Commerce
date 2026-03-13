package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.UserBean;
import gov.iti.jets.ecommerce.config.DataSourceConfig;
import gov.iti.jets.ecommerce.dao.impl.UserDaoImpl;
import gov.iti.jets.ecommerce.entity.User;
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
        UserDaoImpl userDao = new UserDaoImpl(DataSourceConfig.getDataSource());
        Optional<User> user = userDao.findByEmail(email);
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

    public static AuthService getInstance() {
        return instance;
    }
}
