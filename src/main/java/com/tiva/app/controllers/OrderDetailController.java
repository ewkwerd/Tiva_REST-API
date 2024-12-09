package com.tiva.app.controllers;

import com.tiva.app.models.OrderDetail;
import com.tiva.app.services.OrderDetailService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable int id) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        return ResponseEntity.status(200).body(orderDetail);
    }

    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody @Valid OrderDetail orderDetail) {
        OrderDetail createdOrderDetail = orderDetailService.save(orderDetail);
        return ResponseEntity.status(201).body(createdOrderDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable int id, @RequestBody @Valid OrderDetail orderDetailDetails) {
        OrderDetail updatedOrderDetail = orderDetailService.update(id, orderDetailDetails);
        return ResponseEntity.status(200).body(updatedOrderDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable int id) {
        orderDetailService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}