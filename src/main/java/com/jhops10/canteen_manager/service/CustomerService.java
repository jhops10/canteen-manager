package com.jhops10.canteen_manager.service;

import com.jhops10.canteen_manager.dto.customer.CustomerRequestDTO;
import com.jhops10.canteen_manager.dto.customer.CustomerResponseDTO;
import com.jhops10.canteen_manager.dto.customer.CustomerUpdateDTO;
import com.jhops10.canteen_manager.exception.CustomerNotFoundException;
import com.jhops10.canteen_manager.model.Customer;
import com.jhops10.canteen_manager.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer customer = dto.toEntity();
        Customer saved = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(saved);
    }

    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll().stream()
                .map(CustomerResponseDTO::fromEntity)
                .toList();
    }

    public CustomerResponseDTO getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente com o id" + id + " não encontrado."));

        return CustomerResponseDTO.fromEntity(customer);
    }

    public CustomerResponseDTO update(Long id, CustomerUpdateDTO updateDTO) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente com o id" + id + " não encontrado."));

        updateDTO.applyUpdateTo(existing);
        Customer updated = customerRepository.save(existing);
        return CustomerResponseDTO.fromEntity(updated);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Cliente com o id" + id + " não encontrado.");
        }
        customerRepository.deleteById(id);
    }
}
