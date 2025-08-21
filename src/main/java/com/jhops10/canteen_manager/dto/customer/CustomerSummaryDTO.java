package com.jhops10.canteen_manager.dto.customer;

import com.jhops10.canteen_manager.dto.order.OrderResponseDTO;
import com.jhops10.canteen_manager.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public record CustomerSummaryDTO(
        Long customerId,
        String customerName,
        BigDecimal totalDebt,
        List<OrderResponseDTO> orders
) {
    public static CustomerSummaryDTO fromEntity(Customer entity) {
        BigDecimal totalDebt = entity.getOrders().stream()
                .map(order -> order.getProduct().getUnitValue()
                        .multiply(BigDecimal.valueOf(order.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<OrderResponseDTO> orders = entity.getOrders().stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();

        return new CustomerSummaryDTO(
                entity.getId(),
                entity.getName(),
                totalDebt,
                orders
        );
    }
}
