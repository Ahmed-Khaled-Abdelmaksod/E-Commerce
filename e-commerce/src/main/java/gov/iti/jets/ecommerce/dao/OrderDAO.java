package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.enums.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order insert(Order order);

    List<Order> findAll();

    Optional<Order> findById(int id);

    List<Order> findByUserId(int userId);

    List<Order> findByStatus(OrderStatus status);

    boolean updateStatus(int orderId, OrderStatus status);

    boolean delete(int id);
}