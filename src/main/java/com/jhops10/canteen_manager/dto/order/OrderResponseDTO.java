package com.jhops10.canteen_manager.dto.order;

import com.jhops10.canteen_manager.dto.product.ProductResponseDTO;
import com.jhops10.canteen_manager.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderResponseDTO(
        Long id,
        String customerName,
        ProductResponseDTO product,
        Integer quantity,
        BigDecimal totalAmount,
        LocalDate purchaseDate
) {
    public static OrderResponseDTO fromEntity(Order entity) {
        return new OrderResponseDTO(
                entity.getId(),
                entity.getCustomer().getName(),
                ProductResponseDTO.fromEntity(entity.getProduct()),
                entity.getQuantity(),
                entity.getProduct().getUnitValue().multiply(BigDecimal.valueOf(entity.getQuantity())),
                entity.getPurchaseDate()
        );
    }
}
