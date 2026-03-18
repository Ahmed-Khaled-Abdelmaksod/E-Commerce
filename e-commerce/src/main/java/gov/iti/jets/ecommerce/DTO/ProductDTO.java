package gov.iti.jets.ecommerce.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for {@link gov.iti.jets.ecommerce.entity.Product}
 */
public class ProductDTO implements Serializable {
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity = 0;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Integer categoryId;
    private String categoryName;

    public ProductDTO() {
    }

    public ProductDTO(Integer productId, String name, String description, BigDecimal price, Integer stockQuantity, String imageUrl, LocalDateTime createdAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO entity = (ProductDTO) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.stockQuantity, entity.stockQuantity) &&
                Objects.equals(this.imageUrl, entity.imageUrl) &&
                Objects.equals(this.createdAt, entity.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, stockQuantity, imageUrl, createdAt);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "productId = " + productId + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "price = " + price + ", " +
                "stockQuantity = " + stockQuantity + ", " +
                "imageUrl = " + imageUrl + ", " +
                "createdAt = " + createdAt + ")";
    }
}