package com.jhops10.canteen_manager.service;

import com.jhops10.canteen_manager.dto.product.ProductRequestDTO;
import com.jhops10.canteen_manager.dto.product.ProductResponseDTO;
import com.jhops10.canteen_manager.dto.product.ProductUpdateDTO;
import com.jhops10.canteen_manager.exception.ProductNotFoundException;
import com.jhops10.canteen_manager.model.Product;
import com.jhops10.canteen_manager.repository.ProductRepository;
import com.jhops10.canteen_manager.util.ProductFactory;
import org.instancio.Instancio;
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

    @Test
    void getById_shouldReturnProduct_whenIdExists() {
        when(productRepository.findById(defaultId)).thenReturn(Optional.of(defaultProduct));

        ProductResponseDTO sut = productService.getById(defaultId);

        assertThat(sut)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedProductResponseDTO());

        verify(productRepository).findById(defaultId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void getById_shouldThrowException_whenIdDoNotExist() {
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(nonExistingId))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(nonExistingId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct_whenIdExists() {
        ProductUpdateDTO updateDTO = Instancio.of(ProductUpdateDTO.class).create();
        Product updatedProduct = ProductFactory.createDefaultProduct(defaultId);

        when(productRepository.findById(defaultId)).thenReturn(Optional.of(defaultProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ProductResponseDTO sut = productService.update(defaultId, updateDTO);

        assertThat(sut)
                .isNotNull()
                .extracting(
                        ProductResponseDTO::id,
                        ProductResponseDTO::productName,
                        ProductResponseDTO::unitValue
                )
                .containsExactly(
                        updatedProduct.getId(),
                        updatedProduct.getProductName(),
                        updatedProduct.getUnitValue()
                );

        verify(productRepository).findById(defaultId);
        verify(productRepository).save(defaultProduct);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void updateProduct_shouldThrowException_whenIdDoesNotExist() {
        ProductUpdateDTO updateDTO = Instancio.of(ProductUpdateDTO.class).create();

        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(nonExistingId, updateDTO))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(nonExistingId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void deleteProduct_shouldDeleteProduct_whenIdExists() {
        when(productRepository.existsById(defaultId)).thenReturn(true);

        productService.delete(defaultId);

        verify(productRepository).existsById(defaultId);
        verify(productRepository).deleteById(defaultId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void deleteProduct_shouldThrowException_whenIdDoesNotExist() {
        when(productRepository.existsById(nonExistingId)).thenReturn(false);

        assertThatThrownBy(() -> productService.delete(nonExistingId))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).existsById(nonExistingId);
        verifyNoMoreInteractions(productRepository);
    }
}