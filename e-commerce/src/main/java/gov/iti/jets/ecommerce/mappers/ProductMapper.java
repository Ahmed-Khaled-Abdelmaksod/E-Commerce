package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.beans.dashboard.ProductBean;
import gov.iti.jets.ecommerce.DTO.ProductDTO;
import gov.iti.jets.ecommerce.entity.Product;
import gov.iti.jets.ecommerce.entity.Category;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();

        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());

        if (product.getCreatedAt() != null) {
            dto.setCreatedAt(product.getCreatedAt().toLocalDateTime());
        }

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();

        product.setProductId(dto.getProductId() != null ? dto.getProductId() : 0);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        product.setImageUrl(dto.getImageUrl());

        if (dto.getCreatedAt() != null) {
            product.setCreatedAt(Timestamp.valueOf(dto.getCreatedAt()));
        }

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(dto.getCategoryId());
            product.setCategory(category);
        }

        return product;
    }

    public static List<ProductDTO> toDTOList(List<Product> products) {
        return products
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Product> toEntityList(List<ProductDTO> dtos) {
        return dtos
                .stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());
    }


    public static ProductBean toBean(Product entity) {
        if (entity == null) return null;
        ProductBean bean = new ProductBean();
        bean.setProductId(entity.getProductId());
        bean.setName(entity.getName());
        bean.setDescription(entity.getDescription());
        bean.setPrice(entity.getPrice());
        bean.setStockQuantity(entity.getStockQuantity());
        bean.setImageUrl(entity.getImageUrl());
        bean.setHighlighted(entity.isHighlighted());
        
        if (entity.getCategory() != null) {
            bean.setCategoryId(entity.getCategory().getCategoryId());
            bean.setCategoryName(entity.getCategory().getName());
        }
        return bean;
    }

    public static Product toEntity(ProductBean bean) {
        if (bean == null) return null;
        Product entity = new Product();
        
        entity.setProductId(bean.getProductId() != null ? bean.getProductId() : 0);
        entity.setName(bean.getName());
        entity.setDescription(bean.getDescription());
        entity.setPrice(bean.getPrice());
        entity.setStockQuantity(bean.getStockQuantity());
        entity.setImageUrl(bean.getImageUrl());
        entity.setHighlighted(bean.isHighlighted());
        
        if (bean.getCategoryId() != null) {
            Category category = new Category();
            category.setCategoryId(bean.getCategoryId());
            entity.setCategory(category);
        }
        return entity;
    }
}