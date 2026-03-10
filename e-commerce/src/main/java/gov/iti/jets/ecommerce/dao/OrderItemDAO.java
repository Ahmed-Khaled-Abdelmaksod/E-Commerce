package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemDAO {

    OrderItem insert(OrderItem orderItem);

    List<OrderItem> findAll();

    Optional<OrderItem> findById(int id);

    List<OrderItem> findByOrderId(int orderId);

    boolean update(OrderItem orderItem);

    boolean delete(int id);
}