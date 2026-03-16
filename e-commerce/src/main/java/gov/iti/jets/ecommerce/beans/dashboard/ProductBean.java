package gov.iti.jets.ecommerce.beans.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representing a Product for the Admin Dashboard view.
 */
public class ProductBean implements Serializable {
    
    private Integer productId;
    private String name;
    private String categoryName;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;

    // Default Constructor (Required for Java Beans)
    public ProductBean() {}

    // Getters and Setters
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}