package gov.iti.jets.ecommerce.beans.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemBean implements Serializable {
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemBean() {}

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}