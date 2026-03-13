package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.entity.Categorie;
import gov.iti.jets.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDAO {

    public ProductDaoImpl() {
    }

    @Override
    public Product insert(Product product) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(product);
            transaction.commit();
            return product;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to insert product", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> findAll() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Product product = em.find(Product.class, Integer.valueOf(id));
            return Optional.ofNullable(product);
        }
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {

            // 1. Get a lightweight reference to the Category object
            Categorie categoryRef = em.getReference(Categorie.class, Integer.valueOf(categoryId));
            // 2. Query using the object reference, not the nested ID
            TypedQuery<Product> query = em.createQuery(
                    "SELECT p FROM Product p WHERE p.category = :catRef", Product.class);
            query.setParameter("catRef", categoryRef);

            return query.getResultList();
        }
    }

    @Override
    public boolean update(Product product) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, Integer.valueOf(id));
            if (product != null) {
                em.remove(product);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            return false;
        } finally {
            em.close();
        }
    }
}