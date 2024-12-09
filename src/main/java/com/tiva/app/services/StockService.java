package com.tiva.app.services;

import com.tiva.app.exceptions.ConflictException;
import com.tiva.app.exceptions.ResourceNotFoundException;
import com.tiva.app.models.Product;
import com.tiva.app.models.ProductSize;
import com.tiva.app.models.Stock;
import com.tiva.app.repositories.StockRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSizeService productSizeService;

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Stock findById(int id) {
        return stockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + id + " not found"));
    }

    public Stock save(Stock stock) {
        Product product = productService.findById(stock.getProduct().getId());
        stock.setProduct(product);

        ProductSize productSize = productSizeService.findById(stock.getProduct_size().getId());
        stock.setProduct_size(productSize);

        return stockRepository.save(stock);
    }

    @Transactional
    public Stock update(int id, Stock stockDetails) {
        Stock existingStock = stockRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + id + " not found"));

        existingStock.setQuantity(stockDetails.getQuantity());

        Product product = productService.findById(stockDetails.getProduct().getId());
        existingStock.setProduct(product);

        ProductSize productSize = productSizeService.findById(stockDetails.getProduct_size().getId());
        existingStock.setProduct_size(productSize);

        return stockRepository.save(existingStock);
    }

    @Transactional
    public void deleteById(int id) {
        if (!stockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Stock with id " + id + " not found");
        }
        stockRepository.deleteById(id);
    }

    @Transactional
    public void updateStockQuantity(Stock stock, int quantity) {
        int newQuantity = stock.getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new ConflictException("Product quantity not available");
        }
        stock.setQuantity(newQuantity);
        
        stockRepository.save(stock);
    }
}