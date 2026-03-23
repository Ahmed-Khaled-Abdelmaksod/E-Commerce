package gov.iti.jets.ecommerce.mappers;

import gov.iti.jets.ecommerce.DTO.CartItemDTO;
import gov.iti.jets.ecommerce.entity.CartItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartItemMapper {


    public static CartItemDTO toDTO(CartItem entity) {
        if (entity == null) {
            return null;
        }

        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(entity.getCartItemId());
        dto.setQuantity(entity.getQuantity());

        if (entity.getProduct() != null) {
            dto.setProductId(entity.getProduct().getProductId());
            dto.setProductName(entity.getProduct().getName());
            dto.setProductImage(entity.getProduct().getImageUrl());
            dto.setUnitPrice(entity.getProduct().getPrice());


            BigDecimal quantityBD = new BigDecimal(entity.getQuantity());
            dto.setLineTotal(entity.getProduct().getPrice().multiply(quantityBD));
        }

        return dto;
    }

    public static List<CartItemDTO> toDTOList(List<CartItem> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(CartItemMapper::toDTO)
                .collect(Collectors.toList());
    }
}