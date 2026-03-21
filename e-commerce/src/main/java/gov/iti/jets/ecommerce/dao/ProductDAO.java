package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Product;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product insert(Product product);

    List<Product> findAll();

    Optional<Product> findById(int id);

    List<Product> findByCategoryId(EntityManager em , int categoryId);

    List<Product> searchByName(EntityManager em,String name);

    boolean update(Product product);

    boolean delete(int id);
}