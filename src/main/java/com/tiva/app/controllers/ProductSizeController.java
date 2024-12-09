package com.tiva.app.controllers;

import com.tiva.app.models.ProductSize;
import com.tiva.app.services.ProductSizeService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-sizes")
public class ProductSizeController {

    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping
    public List<ProductSize> getAllProductSizes() {
        return productSizeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSize> getProductSizeById(@PathVariable int id) {
        ProductSize productSize = productSizeService.findById(id);
        return ResponseEntity.status(200).body(productSize);
    }

    @PostMapping
    public ResponseEntity<ProductSize> createProductSize(@RequestBody @Valid ProductSize productSize) {
        ProductSize createdProductSize = productSizeService.save(productSize);
        return ResponseEntity.status(201).body(createdProductSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSize> updateProductSize(@PathVariable int id, @RequestBody @Valid ProductSize productSizeDetails) {
        ProductSize updatedProductSize = productSizeService.update(id, productSizeDetails);
        return ResponseEntity.status(200).body(updatedProductSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductSize(@PathVariable int id) {
        productSizeService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}