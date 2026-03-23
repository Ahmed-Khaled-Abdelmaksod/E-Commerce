package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product insert(EntityManager em, Product product);

    List<Product> findAll(EntityManager em);

    Optional<Product> findById(EntityManager em, int id);

    List<Product> findByCategoryId(EntityManager em, int categoryId);

    List<Product> searchByName(EntityManager em, String name);

    boolean update(EntityManager em, Product product);

    boolean delete(EntityManager em, int id);
}