package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.DTO.ProductDTO;
import gov.iti.jets.ecommerce.config.JpaUtil;
import gov.iti.jets.ecommerce.dao.ProductDAO;
import gov.iti.jets.ecommerce.dao.impl.ProductDaoImpl;
import gov.iti.jets.ecommerce.entity.Product;
import gov.iti.jets.ecommerce.mappers.ProductMapper;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductService {

    private static final  ProductService instance;
    static  {
        instance = new ProductService();
    }
    private ProductService() {

    }
    public List<ProductDTO> getAllProducts() {
        ProductDAO productDAO = new ProductDaoImpl();
        try(EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            List<Product> products = productDAO.findAll(em);
            return ProductMapper.toDTOList(products);
        }
    }

    public static ProductService getInstance() {
        return instance;
    }
}
