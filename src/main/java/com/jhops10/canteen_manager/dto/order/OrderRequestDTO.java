package com.jhops10.canteen_manager.dto.order;

import com.jhops10.canteen_manager.model.Customer;
import com.jhops10.canteen_manager.model.Order;
import com.jhops10.canteen_manager.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record OrderRequestDTO(
        @NotNull Long customerId,
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity,
        @NotNull @PastOrPresent LocalDate purchaseDate
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
