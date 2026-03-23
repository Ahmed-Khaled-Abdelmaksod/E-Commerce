package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.OrderItem;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface OrderItemDAO {

    OrderItem insert(EntityManager em, OrderItem orderItem);

    List<OrderItem> findAll(EntityManager em);

    Optional<OrderItem> findById(EntityManager em, int id);

    List<OrderItem> findByOrderId(EntityManager em, int orderId);

    boolean update(EntityManager em, OrderItem orderItem);

    boolean delete(EntityManager em, int id);
}