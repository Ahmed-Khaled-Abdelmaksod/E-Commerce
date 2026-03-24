package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.beans.dashboard.CustomerBean;
import gov.iti.jets.ecommerce.entity.User;

public class CustomerMapper {
    public static CustomerBean toBean(User entity) {
        if (entity == null) return null;
        CustomerBean bean = new CustomerBean();
        bean.setUserId(entity.getUserId());
        bean.setFullName(entity.getFullName());
        bean.setEmail(entity.getEmail());
        bean.setPhone(entity.getPhone());
        bean.setAddress(entity.getAddress());
        bean.setCreditBalance(entity.getCreditBalance());
        return bean;
    }
}