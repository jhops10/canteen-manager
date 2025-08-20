package com.jhops10.canteen_manager.dto.order;

import com.jhops10.canteen_manager.model.Customer;
import com.jhops10.canteen_manager.model.Order;
import com.jhops10.canteen_manager.model.Product;

import java.time.LocalDate;

public record OrderRequestDTO(
        Long customerId,
        Long productId,
        Integer quantity,
        LocalDate purchaseDate
) {
    public Order toEntity(Customer customer, Product product) {
        return Order.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .purchaseDate(purchaseDate)
                .build();
    }
}
