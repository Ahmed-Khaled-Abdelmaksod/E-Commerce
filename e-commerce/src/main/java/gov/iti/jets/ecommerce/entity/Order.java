package gov.iti.jets.ecommerce.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import gov.iti.jets.ecommerce.enums.OrderStatus;

public class Order {

    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    private OrderStatus status;

    public Order() {
    }

    public Order(int orderId, int userId, Timestamp orderDate,
                  BigDecimal totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}