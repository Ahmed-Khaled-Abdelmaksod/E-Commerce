package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.OrderDAO;
import gov.iti.jets.ecommerce.entity.Order;
import gov.iti.jets.ecommerce.enums.OrderStatus;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDAO {

    public OrderDaoImpl() {
    }

    @Override
    public Order insert(EntityManager em, Order order) {
        em.persist(order);
        return order;
    }

    @Override
    public List<Order> findAll(EntityManager em) {
        return em.createQuery("FROM Order o", Order.class).getResultList();
    }

    @Override
    public Optional<Order> findById(EntityManager em, int id) {
        Order order = em.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findByUserId(EntityManager em, int userId) {
        return em.createQuery("FROM Order o WHERE o.user.userId = :userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Order> findByStatus(EntityManager em, OrderStatus status) {
        return em.createQuery("FROM Order o WHERE o.status = :status", Order.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public boolean updateStatus(EntityManager em, int orderId, OrderStatus status) {
        int updatedCount = em.createQuery("UPDATE Order o SET o.status = :status WHERE o.orderId = :id")
                .setParameter("status", status)
                .setParameter("id", orderId)
                .executeUpdate();
        return updatedCount > 0;
    }

    @Override
    public boolean delete(EntityManager em, int id) {
        int deletedCount = em.createQuery("DELETE FROM Order o WHERE o.orderId = :id")
                .setParameter("id", id)
                .executeUpdate();
        return deletedCount > 0;
    }
}