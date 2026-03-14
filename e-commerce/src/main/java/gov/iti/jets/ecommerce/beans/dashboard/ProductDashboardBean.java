package gov.iti.jets.ecommerce.beans.dashboard;

import java.math.BigDecimal;

public class ProductDashboardBean {

    private int productId;
    private String name;
    private BigDecimal price;
    private int stockQuantity;
    private int categoryId;
    // 🟢 تمت إضافة اسم القسم (مثل: Cupcakes, Cakes)
    private String categoryName; 
    private String imageUrl;

    public ProductDashboardBean() {
    }

    public ProductDashboardBean(int productId, String name,
                                 BigDecimal price,
                                 int stockQuantity,
                                 int categoryId,
                                 String categoryName,
                                 String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName; // 🟢
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; } // 🟢
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setProductId(int productId) { this.productId = productId; }
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; } // 🟢
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}