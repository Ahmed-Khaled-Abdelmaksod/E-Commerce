package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.beans.SignUpBean;
import gov.iti.jets.ecommerce.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;
import java.time.Instant;

public class UserMapper {

    public static User mapSignUpBeanToUser(SignUpBean bean) {
        if (bean == null) {
            return null;
        }

        User user = new User();

        user.setFullName(bean.getFullName());
        user.setEmail(bean.getEmail());
        user.setPhone(bean.getPhone());
        user.setBirthday(bean.getBirthday());

        user.setAddress(bean.getAddress());
        user.setRole(bean.getRole());
        user.setCreditBalance(bean.getCreditBalance());
        user.setPasswordHash(BCrypt.hashpw(bean.getPassword(),BCrypt.gensalt()));
        return user;
    }


    public static SignUpBean mapUserToSignUpBean(User user) {
        if (user == null) {
            return null;
        }

        SignUpBean bean = new SignUpBean();

        bean.setFullName(user.getFullName());
        bean.setEmail(user.getEmail());
        bean.setPhone(user.getPhone());

        bean.setBirthday(user.getBirthday());
        bean.setAddress(user.getAddress());
        bean.setRole(user.getRole());
        bean.setCreditBalance(user.getCreditBalance());
        return bean;
    }
}