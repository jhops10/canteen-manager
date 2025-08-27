package com.jhops10.canteen_manager.service;

import com.jhops10.canteen_manager.dto.product.ProductRequestDTO;
import com.jhops10.canteen_manager.dto.product.ProductResponseDTO;
import com.jhops10.canteen_manager.model.Product;
import com.jhops10.canteen_manager.repository.ProductRepository;
import com.jhops10.canteen_manager.util.ProductFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private final Long defaultId = 1L;
    private final Long nonExistingId = 9999999L;
    private Product defaultProduct;

    @BeforeEach
    void setUp() {
        defaultProduct = ProductFactory.createDefaultProduct(defaultId);
    }

    private ProductResponseDTO expectedProductResponseDTO() {
        return new ProductResponseDTO(
                defaultProduct.getId(),
                defaultProduct.getProductName(),
                defaultProduct.getUnitValue()
        );
    }

    @Test
    void createProduct_shouldReturnProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO(defaultProduct.getProductName(), defaultProduct.getUnitValue());

        when(productRepository.save(any(Product.class))).thenReturn(defaultProduct);

        ProductResponseDTO sut = productService.create(requestDTO);

        assertThat(sut)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedProductResponseDTO());

        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void getAll_shouldReturnAllProducts_whenProductsExist() {
        when(productRepository.findAll()).thenReturn(List.of(defaultProduct));

        List<ProductResponseDTO> sut = productService.getAll();

        assertThat(sut)
                .isNotNull()
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(expectedProductResponseDTO());


        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void getAll_shouldReturnEmptyList_whenProductsProductsDoNotExist() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<ProductResponseDTO> sut = productService.getAll();

        assertThat(sut)
                .isNotNull()
                .isEmpty();

        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);
    }
}