package gov.iti.jets.ecommerce.beans.productDetails;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * JavaBean / DTO represents the Product Details page in the storefront.
 * This maps exactly to the UI elements shown in the product page.
 */
public class ProductDetailsBean implements Serializable {

    // Product ID (required for Add to Cart operation)
    private Integer productId;
    
    // Product name (e.g., Chocolate Cake)
    private String name;
    
    // Category name (e.g., Cakes)
    private String categoryName;
    
    // Product description
    private String description;
    
    // Price (e.g., 24.99)
    private BigDecimal price;
    
    // Available quantity in stock (e.g., 15)
    private Integer stockQuantity;
    
    // Product image URL
    private String imageUrl;

    // Default Constructor
    public ProductDetailsBean() {
    }

    // Parameterized Constructor
    public ProductDetailsBean(Integer productId, String name, String categoryName, 
                             String description, BigDecimal price, 
                             Integer stockQuantity, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.categoryName = categoryName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}