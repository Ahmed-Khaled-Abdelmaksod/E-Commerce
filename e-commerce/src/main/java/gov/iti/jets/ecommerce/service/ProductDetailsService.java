package gov.iti.jets.ecommerce.service;

import gov.iti.jets.ecommerce.beans.productDetails.ProductDetailsBean;
import gov.iti.jets.ecommerce.enums.CartItemStatus;

public interface ProductDetailsService {

    /**
     * Retrieves the product details based on its ID
     * to be displayed on the Product Details page.
     *
     * @param productId The ID of the product
     * @return ProductDetailsBean containing the product data, or null if not found
     */
    ProductDetailsBean getProductDetailsById(int productId);

    /**
     * Adds a specific quantity of a product to the user's cart.
     *
     * @param userId The ID of the current logged-in user
     * @param productId The ID of the product to be added
     * @param quantity The quantity to add to the cart
     * @return CartItemStatus representing the result of the operation (e.g., ADDED, LOWQUNATITY, etc.)
     */
    CartItemStatus addProductToCart(int userId, int productId, int quantity);

}