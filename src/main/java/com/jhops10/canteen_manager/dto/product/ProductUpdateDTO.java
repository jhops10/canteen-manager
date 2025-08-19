package com.jhops10.canteen_manager.dto.product;

import com.jhops10.canteen_manager.model.Product;

public record ProductUpdateDTO(
        String productName,
        Double unitValue
) {
    public void applyUpdateTo(Product entity) {
        if (productName != null) entity.setProductName(productName);
        if (unitValue != null) entity.setUnitValue(unitValue);
    }
}
