package gov.iti.jets.ecommerce.beans.dashboard;

import java.math.BigDecimal;

public class OrderItemDashboardBean {

    private int orderItemId;
    private int orderId;
    private int productId;
    // 🟢 تمت إضافة اسم المنتج عشان يتعرض جوه الطلب (مثال: Chocolate Cake)
    private String productName; 
    private int quantity;
    private BigDecimal price;

    public OrderItemDashboardBean() {
    }

    public OrderItemDashboardBean(int orderItemId,
                                   int orderId,
                                   int productId,
                                   String productName,
                                   int quantity,
                                   BigDecimal price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName; // 🟢
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public int getOrderItemId() { return orderItemId; }
    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; } // 🟢
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }

    // Setters
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; } // 🟢
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
}