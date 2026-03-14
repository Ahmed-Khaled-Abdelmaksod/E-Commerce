package gov.iti.jets.ecommerce.beans.dashboard;

import java.math.BigDecimal;
import gov.iti.jets.ecommerce.enums.UserRole;

public class UserDashboardBean {

    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String address; 
    private BigDecimal creditBalance;
    private UserRole role;

    public UserDashboardBean() {
    }

    public UserDashboardBean(int userId, String fullName,
                             String email, String phone, String address,
                             BigDecimal creditBalance,
                             UserRole role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.creditBalance = creditBalance;
        this.role = role;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public BigDecimal getCreditBalance() { return creditBalance; }
    public UserRole getRole() { return role; }

    // Setters
    public void setUserId(int userId) { this.userId = userId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setCreditBalance(BigDecimal creditBalance) { this.creditBalance = creditBalance; }
    public void setRole(UserRole role) { this.role = role; }
}