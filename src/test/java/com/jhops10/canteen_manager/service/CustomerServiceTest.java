package com.jhops10.canteen_manager.service;

import com.jhops10.canteen_manager.dto.customer.CustomerRequestDTO;
import com.jhops10.canteen_manager.dto.customer.CustomerResponseDTO;
import com.jhops10.canteen_manager.dto.order.OrderResponseDTO;
import com.jhops10.canteen_manager.exception.CustomerNotFoundException;
import com.jhops10.canteen_manager.model.Customer;
import com.jhops10.canteen_manager.repository.CustomerRepository;
import com.jhops10.canteen_manager.util.CustomerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private final Long defaultId = 1L;
    private final Long nonExistingId = 9999999L;
    private Customer defaultCustomer;

    @BeforeEach
    void setUp() {
        defaultCustomer = CustomerFactory.createDefaultCustomer(defaultId);
    }

    private CustomerResponseDTO expectedCustomerResponseDTO() {
        List<OrderResponseDTO> orders = defaultCustomer.getOrders().stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();

        return new CustomerResponseDTO(
                defaultCustomer.getId(),
                defaultCustomer.getName(),
                orders
        );
    }

    @Test
    void createCustomer_shouldReturnCustomer() {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO(defaultCustomer.getName());
        when(customerRepository.save(any(Customer.class))).thenReturn(defaultCustomer);

        CustomerResponseDTO sut = customerService.create(requestDTO);

        assertThat(sut)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedCustomerResponseDTO());

        verify(customerRepository).save(any(Customer.class));
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getAll_shouldReturnAllCustomers_whenCustomersExists() {
        when(customerRepository.findAll()).thenReturn(List.of(defaultCustomer));

        List<CustomerResponseDTO> sut = customerService.getAll();

        assertThat(sut)
                .isNotNull()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(expectedCustomerResponseDTO());

        verify(customerRepository).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getAll_shouldReturnEmptyList_whenCustomersDoNotExist() {
        when(customerRepository.findAll()).thenReturn(List.of());

        List<CustomerResponseDTO> sut = customerService.getAll();

        assertThat(sut)
                .isNotNull()
                .isEmpty();

        verify(customerRepository).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getById_shouldReturnCustomer_whenIdExists() {
        when(customerRepository.findById(defaultId)).thenReturn(Optional.of(defaultCustomer));

        CustomerResponseDTO sut = customerService.getById(defaultId);

        assertThat(sut)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedCustomerResponseDTO());

        verify(customerRepository).findById(defaultId);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getById_shouldThrowException_whenIdDoNotExist() {
        when(customerRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getById(nonExistingId))
                .isInstanceOf(CustomerNotFoundException.class);

        verify(customerRepository).findById(nonExistingId);
        verifyNoMoreInteractions(customerRepository);
    }

}