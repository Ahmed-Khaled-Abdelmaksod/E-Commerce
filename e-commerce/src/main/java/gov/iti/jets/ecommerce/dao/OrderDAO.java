package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    Order insert(Order order);

    List<Order> findAll();

    Optional<Order> findById(int id);

    List<Order> findByUserId(int userId);

    boolean update(Order order);

    boolean delete(int id);
}