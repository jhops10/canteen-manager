package com.jhops10.canteen_manager.dto.product;

import com.jhops10.canteen_manager.model.Product;

public record ProductResponseDTO(
        Long id,
        String productName,
        Double unitValue
) {
    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getUnitValue()
        );
    }
}
