package com.jhops10.canteen_manager.dto.product;

import com.jhops10.canteen_manager.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank String productName,
        @NotNull @Positive BigDecimal unitValue
) {
    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .unitValue(unitValue)
                .build();
    }
}
