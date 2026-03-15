package gov.iti.jets.ecommerce.beans;

import gov.iti.jets.ecommerce.enums.UserRole;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;

public class UserBean implements Serializable {
    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String address;
    private UserRole role;
    private BigDecimal creditBalance;

    // Constructors
    public UserBean() {}

    // Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public BigDecimal getCreditBalance() { return creditBalance; }
    public void setCreditBalance(BigDecimal creditBalance) { this.creditBalance = creditBalance; }
}