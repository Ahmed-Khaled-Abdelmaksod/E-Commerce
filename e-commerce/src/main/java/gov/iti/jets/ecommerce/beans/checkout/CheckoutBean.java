package gov.iti.jets.ecommerce.beans.checkout;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representing the entire Checkout page data.
 */
public class CheckoutBean implements Serializable {

    // --- Delivery Details ---
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;

    // --- Payment Summary ---
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal total;
    private BigDecimal userCredit;

    // --- Order Items ---
    private List<CheckoutItemBean> orderItems;

    // Default Constructor (Required for Java Beans)
    public CheckoutBean() {}

    // Getters and Setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(BigDecimal deliveryFee) { this.deliveryFee = deliveryFee; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getUserCredit() { return userCredit; }
    public void setUserCredit(BigDecimal userCredit) { this.userCredit = userCredit; }

    public List<CheckoutItemBean> getOrderItems() { return orderItems; }
    public void setOrderItems(List<CheckoutItemBean> orderItems) { this.orderItems = orderItems; }
}