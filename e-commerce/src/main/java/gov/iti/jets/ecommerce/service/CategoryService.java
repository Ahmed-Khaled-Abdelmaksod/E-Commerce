package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.DTO.CategoryDTO;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.impl.CategoryDaoImpl;
import gov.iti.jets.ecommerce.entity.Category;
import gov.iti.jets.ecommerce.mappers.CategoryMapper;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryService {
    static private CategoryService instance;

    static {
        instance = new CategoryService();
    }

    private CategoryService() {

    }
    public List<CategoryDTO> getAllCatigories() {
        try(EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<Category> categories = new CategoryDaoImpl().findAll(em);
            return CategoryMapper.toDTOList(categories);
        }
    }


    public static CategoryService getInstance() {
        return instance;
    }
}
