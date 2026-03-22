package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.enums.OrderStatus;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order insert(EntityManager em, Order order);

    List<Order> findAll(EntityManager em);

    Optional<Order> findById(EntityManager em, int id);

    List<Order> findByUserId(EntityManager em, int userId);

    List<Order> findByStatus(EntityManager em, OrderStatus status);

    boolean updateStatus(EntityManager em, int orderId, OrderStatus status);

    boolean delete(EntityManager em, int id);
}