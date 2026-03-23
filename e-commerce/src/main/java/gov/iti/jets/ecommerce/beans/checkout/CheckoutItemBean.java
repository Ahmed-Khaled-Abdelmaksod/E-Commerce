package gov.iti.jets.ecommerce.beans.checkout;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representing a single order item for the Checkout view.
 */
public class CheckoutItemBean implements Serializable {

    private String productName;
    private String imageUrl;
    private Integer quantity;
    private BigDecimal lineTotal;

    // Default Constructor (Required for Java Beans)
    public CheckoutItemBean() {}

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getLineTotal() { return lineTotal; }
    public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}