package gov.iti.jets.ecommerce.service;

import java.math.BigDecimal;

import gov.iti.jets.ecommerce.beans.checkout.CheckoutBean;

public interface CheckoutService {

    /**
     * Retrieves all necessary data to populate the Checkout page for a given user.
     * @param userId The ID of the user proceeding to checkout.
     * @return CheckoutBean containing delivery details, payment summary, and order items.
     */
    CheckoutBean getCheckoutDetails(int userId);

    /**
     * Processes the final checkout, deducts from user credit, updates product stock, 
     * and changes the order status.
     * @param userId The ID of the user placing the order.
     * @return boolean indicating whether the checkout process was successful.
     */
    boolean processCheckout(int userId);
    
    /**
     * Retrieves the user's current credit balance after checkout.
     * @param userId The ID of the user.
     * @return The updated credit balance of the user.
     */
    BigDecimal getUserBalanceAfterCheckout(int userId);
}