package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    Category insert(Category category);

    List<Category> findAll();

    Optional<Category> findById(int id);

    boolean update(Category category);

    boolean delete(int id);

    boolean existsByName(String name);
}