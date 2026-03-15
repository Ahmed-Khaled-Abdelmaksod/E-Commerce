package gov.iti.jets.ecommerce.entity;

import gov.iti.jets.ecommerce.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Order {

    private Integer orderId;
    private User user;
    private BigDecimal totalPrice;
    private OrderStatus status = OrderStatus.PENDING;
    private LocalDateTime orderDate;

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    
}