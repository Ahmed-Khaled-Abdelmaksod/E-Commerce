package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.CategoryDAO;
import gov.iti.jets.ecommerce.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl implements CategoryDAO {

    public CategoryDaoImpl() {

    }

    @Override
    public Category insert(Category categorie) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(categorie);
            transaction.commit();
            return categorie;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to insert category", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findAll() {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        }
    }

    @Override
    public Optional<Category> findById(int id) {
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            Category categorie = em.find(Category.class, Integer.valueOf(id));
            return Optional.ofNullable(categorie);
        }
    }

    @Override
    public boolean update(Category categorie) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(categorie);
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
            // Using Integer.valueOf(id) here as well
            Category categorie = em.find(Category.class, Integer.valueOf(id));
            if (categorie != null) {
                em.remove(categorie);
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

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}