package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product insert(Product product);

    List<Product> findAll();

    Optional<Product> findById(int id);

    List<Product> findByCategoryId(int categoryId);

    List<Product> searchByName(String name);

    boolean update(Product product);

    boolean delete(int id);
}