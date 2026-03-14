package gov.iti.jets.ecommerce.beans.dashboard;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import gov.iti.jets.ecommerce.enums.OrderStatus;

public class OrderDashboardBean {

    private int orderId;
    private int userId;
    // 🟢 تمت إضافة اسم العميل (مثال: by Jane Doe)
    private String customerName; 
    private Timestamp orderDate;
    private BigDecimal totalPrice;
    private OrderStatus status;
    // 🟢 تمت إضافة قائمة المنتجات اللي جوه الطلب عشان تظهر كـ "Pills" في الـ UI
    private List<OrderItemDashboardBean> orderItems; 

    public OrderDashboardBean() {
    }

    public OrderDashboardBean(int orderId,
                               int userId,
                               String customerName,
                               Timestamp orderDate,
                               BigDecimal totalPrice,
                               OrderStatus status,
                               List<OrderItemDashboardBean> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.customerName = customerName; // 🟢
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems; // 🟢
    }

    // Getters
    public int getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public String getCustomerName() { return customerName; } // 🟢
    public Timestamp getOrderDate() { return orderDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public OrderStatus getStatus() { return status; }
    public List<OrderItemDashboardBean> getOrderItems() { return orderItems; } // 🟢

    // Setters
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; } // 🟢
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setOrderItems(List<OrderItemDashboardBean> orderItems) { this.orderItems = orderItems; } // 🟢
}