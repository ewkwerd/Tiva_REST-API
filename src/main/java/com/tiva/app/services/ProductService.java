package com.tiva.app.services;

import com.tiva.app.exceptions.ConflictException;
import com.tiva.app.exceptions.ResourceNotFoundException;
import com.tiva.app.models.Category;
import com.tiva.app.models.Product;
import com.tiva.app.repositories.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(int id) {
        return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public Product save(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new ConflictException("Product with name " + product.getName() + " already exists");
        }

        Category category = categoryService.findById(product.getCategory().getId());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Transactional
    public Product update(int id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        
        Category category = categoryService.findById(productDetails.getCategory().getId());
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteById(int id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}