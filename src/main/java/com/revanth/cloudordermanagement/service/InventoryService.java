
package com.revanth.cloudordermanagement.service;

import com.revanth.cloudordermanagement.entity.Product;
import com.revanth.cloudordermanagement.exception.ResourceNotFoundException;
import com.revanth.cloudordermanagement.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getInventory(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with ID: " + productId));
    }

    @Transactional
    public Product updateInventory(Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with ID: " + productId));

        product.setQuantity(quantity);

        return productRepository.save(product);
    }
}
