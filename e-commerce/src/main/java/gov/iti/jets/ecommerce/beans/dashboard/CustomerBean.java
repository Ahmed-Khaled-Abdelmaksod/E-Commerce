package gov.iti.jets.ecommerce.beans.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representing a Customer for the Admin Dashboard view.
 */
public class CustomerBean implements Serializable {

    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private BigDecimal creditBalance;

    // Default Constructor
    public CustomerBean() {}

    // Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BigDecimal getCreditBalance() { return creditBalance; }
    public void setCreditBalance(BigDecimal creditBalance) { this.creditBalance = creditBalance; }
}