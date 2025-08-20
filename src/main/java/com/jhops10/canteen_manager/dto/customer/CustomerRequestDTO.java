package com.jhops10.canteen_manager.dto.customer;

import com.jhops10.canteen_manager.model.Customer;

import java.util.ArrayList;

public record CustomerRequestDTO(
        String name
) {
    public Customer toEntity() {
        return Customer.builder()
                .name(name)
                .orders(new ArrayList<>())
                .build();
    }
}
