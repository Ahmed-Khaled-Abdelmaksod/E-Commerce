package gov.iti.jets.ecommerce.beans.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderBean implements Serializable {
    private Integer orderId;
    private String customerName;
    private Date orderDate;
    private BigDecimal totalPrice;
    
    private List<OrderItemBean> orderItems;

    public OrderBean() {}

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public List<OrderItemBean> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemBean> orderItems) { this.orderItems = orderItems; }
}