package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.OrderItemDAO;
import gov.iti.jets.ecommerce.entity.OrderItem;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class OrderItemDaoImpl implements OrderItemDAO {

    public OrderItemDaoImpl() {
    }

    @Override
    public OrderItem insert(EntityManager em, OrderItem orderItem) {
        em.persist(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> findAll(EntityManager em) {
        return em.createQuery("FROM OrderItem oi", OrderItem.class).getResultList();
    }

    @Override
    public Optional<OrderItem> findById(EntityManager em, int id) {
        OrderItem orderItem = em.find(OrderItem.class, id);
        return Optional.ofNullable(orderItem);
    }

    @Override
    public List<OrderItem> findByOrderId(EntityManager em, int orderId) {
        return em.createQuery("FROM OrderItem oi WHERE oi.order.orderId = :orderId", OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    @Override
    public boolean update(EntityManager em, OrderItem orderItem) {
        if (orderItem != null && orderItem.getOrderItemId() != null) {
            em.merge(orderItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(EntityManager em, int id) {
        int deletedCount = em.createQuery("DELETE FROM OrderItem oi WHERE oi.orderItemId = :id")
                .setParameter("id", id)
                .executeUpdate();
        return deletedCount > 0;
    }
}