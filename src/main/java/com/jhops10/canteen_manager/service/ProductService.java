package com.jhops10.canteen_manager.service;

import com.jhops10.canteen_manager.dto.product.ProductRequestDTO;
import com.jhops10.canteen_manager.dto.product.ProductResponseDTO;
import com.jhops10.canteen_manager.dto.product.ProductUpdateDTO;
import com.jhops10.canteen_manager.exception.ProductNotFoundException;
import com.jhops10.canteen_manager.model.Product;
import com.jhops10.canteen_manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = dto.toEntity();
        Product saved = productRepository.save(product);
        return ProductResponseDTO.fromEntity(saved);
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com id " + id + " não encontrado."));
        return ProductResponseDTO.fromEntity(product);
    }

    public ProductResponseDTO update(Long id, ProductUpdateDTO updateDTO) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com id " + id + " não encontrado."));

        updateDTO.applyUpdateTo(existing);
        Product updated = productRepository.save(existing);
        return ProductResponseDTO.fromEntity(updated);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto com id " + id + " não encontrado.");
        }
        productRepository.deleteById(id);
    }


}
