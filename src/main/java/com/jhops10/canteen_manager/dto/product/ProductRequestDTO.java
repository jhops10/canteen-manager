package com.jhops10.canteen_manager.dto.product;

import com.jhops10.canteen_manager.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequestDTO(
        @NotBlank String productName,
        @NotNull @Positive Double unitValue
) {
    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .unitValue(unitValue)
                .build();
    }
}
