package com.jhops10.canteen_manager.service;

import com.jhops10.canteen_manager.dto.order.OrderRequestDTO;
import com.jhops10.canteen_manager.dto.order.OrderResponseDTO;
import com.jhops10.canteen_manager.exception.CustomerNotFoundException;
import com.jhops10.canteen_manager.exception.OrderNotFoundException;
import com.jhops10.canteen_manager.exception.ProductNotFoundException;
import com.jhops10.canteen_manager.model.Customer;
import com.jhops10.canteen_manager.model.Order;
import com.jhops10.canteen_manager.model.Product;
import com.jhops10.canteen_manager.repository.CustomerRepository;
import com.jhops10.canteen_manager.repository.OrderRepository;
import com.jhops10.canteen_manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    public OrderResponseDTO create(OrderRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ProductNotFoundException("Produto com id " + dto.productId() + " não encontrado."));

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Cliente com id " + dto.customerId() + " não encontrado."));

        Order order = dto.toEntity(customer, product);

        orderRepository.save(order);
        return OrderResponseDTO.fromEntity(order);
    }

    public List<OrderResponseDTO> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();
    }

    public OrderResponseDTO getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido com o id " + id + " não encontrado."));

        return OrderResponseDTO.fromEntity(order);
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Pedido com o id " + id + " não encontrado.");
        }
        orderRepository.deleteById(id);
    }

    public void deleteOrdersByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente com id " + customerId + " não encontrado."));

        List<Order> orders = orderRepository.findByCustomerId(customerId);

        orderRepository.deleteAll(orders);
    }
}
