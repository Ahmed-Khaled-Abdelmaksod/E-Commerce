package gov.iti.jets.ecommerce.dao;

import gov.iti.jets.ecommerce.entity.Category;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    Category insert(EntityManager em,Category category);

    List<Category> findAll(EntityManager em);

    Optional<Category> findById(EntityManager em,int id);

    boolean update(EntityManager em,Category category);

    boolean delete(EntityManager em,int id);

    boolean existsByName(EntityManager em,String name);
}