package com.jhops10.canteen_manager.dto.customer;

import com.jhops10.canteen_manager.dto.order.OrderResponseDTO;
import com.jhops10.canteen_manager.model.Customer;

import java.util.List;

public record CustomerResponseDTO(
        Long id,
        String name,
        List<OrderResponseDTO> orders
) {
    public static CustomerResponseDTO fromEntity(Customer entity) {
        return new CustomerResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getOrders().stream()
                        .map(OrderResponseDTO::fromEntity)
                        .toList()
        );
    }
}
