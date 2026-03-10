package gov.iti.jets.ecommerce.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import gov.iti.jets.ecommerce.enums.UserRole;

public class User {

    private int userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private String phone;
    private Date birthday;
    private String address;
    private UserRole role;
    private BigDecimal creditBalance;
    private Timestamp createdAt;

    public User() {
    }

    public User(int userId, String fullName, String email, String passwordHash,
                 String phone, Date birthday, String address,
                 UserRole role, BigDecimal creditBalance, Timestamp createdAt) {

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.role = role;
        this.creditBalance = creditBalance;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}