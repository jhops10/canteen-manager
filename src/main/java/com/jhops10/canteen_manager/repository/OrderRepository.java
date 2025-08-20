package com.jhops10.canteen_manager.repository;

import com.jhops10.canteen_manager.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
