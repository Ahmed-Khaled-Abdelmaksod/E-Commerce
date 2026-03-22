package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.CategoryDAO;
import gov.iti.jets.ecommerce.entity.Category;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl implements CategoryDAO {

    public CategoryDaoImpl() {
    }

    @Override
    public Category insert(EntityManager em, Category category) {
        em.persist(category);
        return category;
    }

    @Override
    public List<Category> findAll(EntityManager em) {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    @Override
    public Optional<Category> findById(EntityManager em, int id) {
        Category category = em.find(Category.class, id);
        return Optional.ofNullable(category);
    }

    @Override
    public boolean update(EntityManager em, Category category) {
        if (category != null && category.getCategoryId() != 0) {
            em.merge(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(EntityManager em, int id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByName(EntityManager em, String name) {
        Long count = em.createQuery("SELECT COUNT(c) FROM Category c WHERE c.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }
}