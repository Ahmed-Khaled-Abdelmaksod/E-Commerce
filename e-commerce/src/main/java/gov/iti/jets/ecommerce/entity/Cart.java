package gov.iti.jets.ecommerce.entity;

import java.time.LocalDateTime;
public class Cart {

    private Integer cartId;
    private User user;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Integer getCartId() { return cartId; }
    public void setCartId(Integer cartId) { this.cartId = cartId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}