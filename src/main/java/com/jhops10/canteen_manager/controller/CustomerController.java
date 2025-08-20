package com.jhops10.canteen_manager.controller;

import com.jhops10.canteen_manager.dto.customer.CustomerRequestDTO;
import com.jhops10.canteen_manager.dto.customer.CustomerResponseDTO;
import com.jhops10.canteen_manager.dto.customer.CustomerUpdateDTO;
import com.jhops10.canteen_manager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody CustomerRequestDTO requestDTO) {
        CustomerResponseDTO created = customerService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        List<CustomerResponseDTO> customers = customerService.getAll();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable("id") Long id) {
        CustomerResponseDTO customer = customerService.getById(id);
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable("id") Long id, @RequestBody CustomerUpdateDTO updateDTO) {
        CustomerResponseDTO updated = customerService.update(id, updateDTO);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
