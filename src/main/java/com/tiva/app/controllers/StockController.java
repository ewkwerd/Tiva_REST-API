package com.tiva.app.controllers;

import com.tiva.app.models.Stock;
import com.tiva.app.services.StockService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable int id) {
        Stock stock = stockService.findById(id);
        return ResponseEntity.status(200).body(stock);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody @Valid Stock stock) {
        Stock createdStock = stockService.save(stock);
        return ResponseEntity.status(201).body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable int id, @RequestBody @Valid Stock stockDetails) {
        Stock updatedStock = stockService.update(id, stockDetails);
        return ResponseEntity.status(200).body(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable int id) {
        stockService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}