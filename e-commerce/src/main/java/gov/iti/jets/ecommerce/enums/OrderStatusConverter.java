package gov.iti.jets.ecommerce.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts between the OrderStatus enum and the DB ENUM string case-insensitively.
 * The DB stores lowercase ('pending','paid','completed','cancelled') but the Java
 * enum constants are uppercase (PENDING, PAID, etc.).
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        return status != null ? status.name().toLowerCase() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return OrderStatus.valueOf(dbValue.toUpperCase());
    }
}
