package gov.iti.jets.ecommerce.dao.impl;

import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.entity.Category;
import gov.iti.jets.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDAO {

    public ProductDaoImpl() {
    }

    @Override
    public Product insert(EntityManager em,Product product) {
            em.persist(product);
            return product;
    }

    @Override
    public List<Product> findAll(EntityManager em) {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Optional<Product> findById(EntityManager em,int id) {
        Product product = em.find(Product.class, id);
        return Optional.ofNullable(product);
    }

//    --- NOT CURRENTLY USED BY DASHBOARD ---
    @Override
    public List<Product> findByCategoryId(EntityManager em,int categoryId) {
        Query query= em.createQuery("FROM Product p where p.category.categoryId = :categoryId", Product.class);
        query.setParameter("categoryId",categoryId);
        return query.getResultList();
    }

    @Override
    public List<Product> searchByName(EntityManager em,String name) {
        Query query= em.createQuery("FROM Product p where p.name = :name", Product.class);
        query.setParameter("name",name);
        return query.getResultList();
    }
//    ------------------------------------------

    // This matches your Dashboard logic for filtering products by category
    public List<Product> findByCategory(EntityManager em,int categoryId) {
        Category categoryRef = em.getReference(Category.class, categoryId);
        TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE p.category = :catRef", Product.class);
        query.setParameter("catRef", categoryRef);

        return query.getResultList();

    }

    @Override
    public boolean update(EntityManager em,Product product) {
        if (product == null) {
            return false;
        }
        Product merged = em.merge(product);
        return merged != null;
    }

    @Override
    public boolean delete(EntityManager em,int id) {
        int deletedCount = em.createQuery("DELETE FROM Product p WHERE p.productId = :id")
                .setParameter("id", id)
                .executeUpdate();
        return deletedCount > 0;
    }
}