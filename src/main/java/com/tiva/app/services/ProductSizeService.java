package com.tiva.app.services;

import com.tiva.app.exceptions.ConflictException;
import com.tiva.app.exceptions.ResourceNotFoundException;
import com.tiva.app.models.ProductSize;
import com.tiva.app.repositories.ProductSizeRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;

    public List<ProductSize> findAll() {
        return productSizeRepository.findAll();
    }

    public ProductSize findById(int id) {
        return productSizeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product size with id " + id + " not found"));
    }

    public ProductSize save(ProductSize productSize) {
        if (productSizeRepository.findByName(productSize.getName()).isPresent()) {
            throw new ConflictException("Product size with name " + productSize.getName() + " already exists");
        }

        return productSizeRepository.save(productSize);
    }

    @Transactional
    public ProductSize update(int id, ProductSize productSizeDetails) {
        ProductSize existingProductSize = productSizeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product size with id " + id + " not found"));

        existingProductSize.setName(productSizeDetails.getName());
        return productSizeRepository.save(existingProductSize);
    }

    @Transactional
    public void deleteById(int id) {
        if (!productSizeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product size with id " + id + " not found");
        }
        productSizeRepository.deleteById(id);
    }
}