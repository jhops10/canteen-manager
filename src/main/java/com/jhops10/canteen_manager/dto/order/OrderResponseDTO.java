package com.jhops10.canteen_manager.dto.order;

import com.jhops10.canteen_manager.dto.product.ProductResponseDTO;
import com.jhops10.canteen_manager.model.Order;

import java.time.LocalDate;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        ProductResponseDTO product,
        Integer quantity,
        LocalDate purchaseDate
) {
    public static OrderResponseDTO fromEntity(Order entity) {
        return new OrderResponseDTO(
                entity.getId(),
                entity.getCustomer().getId(),
                ProductResponseDTO.fromEntity(entity.getProduct()),
                entity.getQuantity(),
                entity.getPurchaseDate()
        );
    }
}
