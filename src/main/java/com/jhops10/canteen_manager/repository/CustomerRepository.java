package com.jhops10.canteen_manager.repository;

import com.jhops10.canteen_manager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
