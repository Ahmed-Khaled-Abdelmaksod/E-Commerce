package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CategoryDAO;
import gov.iti.jets.ecommerce.entity.Category;
import gov.iti.jets.ecommerce.entity.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl implements CategoryDAO {
    @Override
    public Category insert(EntityManager em, Category category) {
        em.persist(category);
        return category;
    }

    @Override
    public List<Category> findAll(EntityManager em) {
        return em.createQuery("from Category c", Category.class).getResultList();
    }

    @Override
    public Optional<Category> findById(EntityManager em, int id) {
        Category category = em.find(Category.class,id);
        return Optional.ofNullable(category);
    }

    @Override
    public boolean update(EntityManager em, Category category) {
        if (category == null) {
            return false;
        }
        Category merged = em.merge(category);
        return merged != null;
    }

    @Override
    public boolean delete(EntityManager em, int id) {
        int deletedCount = em.createQuery("DELETE FROM Category c WHERE c.categoryId = :id")
                .setParameter("id", id)
                .executeUpdate();
        return deletedCount > 0;
    }

    @Override
    public boolean existsByName(EntityManager em, String name) {
        return false;
    }
}
