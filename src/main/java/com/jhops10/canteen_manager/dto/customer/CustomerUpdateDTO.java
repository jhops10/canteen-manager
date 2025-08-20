package com.jhops10.canteen_manager.dto.customer;

import com.jhops10.canteen_manager.model.Customer;

public record CustomerUpdateDTO(
        String customerName
) {
    public void applyUpdateTo(Customer entity) {
        if (customerName != null) entity.setName(customerName);
    }
}
