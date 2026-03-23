package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.entity.Category;
import gov.iti.jets.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDAO {

    public ProductDaoImpl() {
    }

    @Override
    public Product insert(EntityManager em, Product product) {
        em.persist(product);
        return product;
    }

    @Override
    public List<Product> findAll(EntityManager em) {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Optional<Product> findById(EntityManager em, int id) {
        Product product = em.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findByCategoryId(EntityManager em, int categoryId) {
        Category categoryRef = em.getReference(Category.class, categoryId);
        TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE p.category = :catRef", Product.class);
        query.setParameter("catRef", categoryRef);
        return query.getResultList();
    }

    @Override
    public List<Product> searchByName(EntityManager em, String name) {
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public boolean update(EntityManager em, Product product) {
        if (product != null) {
            em.merge(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(EntityManager em, int id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
            return true;
        }
        return false;
    }
}