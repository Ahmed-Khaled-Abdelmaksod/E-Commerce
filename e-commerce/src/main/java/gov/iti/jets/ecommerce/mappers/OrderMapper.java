package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.beans.dashboard.OrderBean;
import gov.iti.jets.ecommerce.beans.dashboard.OrderItemBean;
import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.entity.OrderItem;

public class OrderMapper {
    public static OrderBean toBean(Order order) {
        if (order == null) return null;
        OrderBean bean = new OrderBean();
        bean.setOrderId(order.getOrderId());
        bean.setTotalPrice(order.getTotalPrice());
        if (order.getOrderDate() != null) {
            bean.setOrderDate(java.sql.Timestamp.valueOf(order.getOrderDate()));
        }
        if (order.getUser() != null) {
            bean.setCustomerName(order.getUser().getFullName());
        }
        return bean;
    }

    public static OrderItemBean toItemBean(OrderItem item) {
        if (item == null) return null;
        OrderItemBean bean = new OrderItemBean();
        if (item.getProduct() != null) {
            bean.setProductName(item.getProduct().getName());
        }
        bean.setQuantity(item.getQuantity());
        bean.setPrice(item.getPrice());
        return bean;
    }
}