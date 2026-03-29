package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.beans.productDetails.ProductDetailsBean;
import gov.iti.jets.ecommerce.entity.Product;

/**
 * Mapper class responsible for converting Product entity 
 * to ProductDetailsBean for the Product Details page.
 */
public class ProductDetailsMapper {

    /**
     * Converts a Product entity into a ProductDetailsBean.
     *
     * @param entity The Product entity fetched from the database
     * @return A populated ProductDetailsBean, or null if the entity is null
     */
    public static ProductDetailsBean toBean(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductDetailsBean bean = new ProductDetailsBean();
        
        bean.setProductId(entity.getProductId());
        bean.setName(entity.getName());
        bean.setDescription(entity.getDescription());
        bean.setPrice(entity.getPrice());
        bean.setStockQuantity(entity.getStockQuantity());
        bean.setImageUrl(entity.getImageUrl());
        
        // Map the category name if the category exists
        if (entity.getCategory() != null) {
            bean.setCategoryName(entity.getCategory().getName());
        }
        
        return bean;
    }
}