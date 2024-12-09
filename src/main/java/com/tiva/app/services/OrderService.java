package com.tiva.app.services;

import com.tiva.app.exceptions.ConflictException;
import com.tiva.app.exceptions.ResourceNotFoundException;
import com.tiva.app.models.Order;
import com.tiva.app.repositories.OrderRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(int id) {
        return orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order update(int id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));

        existingOrder.setDate(orderDetails.getDate());
        existingOrder.setTotal(orderDetails.getTotal());

        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteById(int id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order with id " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public void calculateTotal(Order order, double subtotal) {
        double newTotal = order.getTotal() + subtotal;
        if (newTotal < 0) {
            throw new ConflictException("The calculated total is invalid");
        }
        order.setTotal(newTotal);

        orderRepository.save(order);
    }
}