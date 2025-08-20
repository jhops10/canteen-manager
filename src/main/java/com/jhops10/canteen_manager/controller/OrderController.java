package com.jhops10.canteen_manager.controller;

import com.jhops10.canteen_manager.dto.order.OrderRequestDTO;
import com.jhops10.canteen_manager.dto.order.OrderResponseDTO;
import com.jhops10.canteen_manager.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO requestDTO) {
        OrderResponseDTO created = orderService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        List<OrderResponseDTO> orders = orderService.getAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable("id") Long id) {
        OrderResponseDTO order = orderService.getById(id);
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
