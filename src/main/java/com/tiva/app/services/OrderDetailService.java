package com.tiva.app.services;

import com.tiva.app.exceptions.ResourceNotFoundException;
import com.tiva.app.models.Order;
import com.tiva.app.models.OrderDetail;
import com.tiva.app.models.Stock;
import com.tiva.app.repositories.OrderDetailRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderService orderService;

    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail findById(int id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order detail with id " + id + " not found"));
    }

    public OrderDetail save(OrderDetail orderDetail) {
        Stock stock = stockService.findById(orderDetail.getStock().getId());
        orderDetail.setStock(stock);

        Order order = orderService.findById(orderDetail.getOrder().getId());
        orderDetail.setOrder(order);

        calculateSubtotal(orderDetail, stock.getProduct().getPrice());
        stockService.updateStockQuantity(stock, orderDetail.getQuantity());
        orderService.calculateTotal(order, orderDetail.getSubtotal());

        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public OrderDetail update(int id, OrderDetail orderDetailDetails) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order detail with id " + id + " not found"));

        Stock existingStock = existingOrderDetail.getStock();
        stockService.updateStockQuantity(existingStock, -existingOrderDetail.getQuantity());

        Order existingOrder = existingOrderDetail.getOrder();
        orderService.calculateTotal(existingOrder, -existingOrderDetail.getSubtotal());

        Stock newStock = stockService.findById(orderDetailDetails.getStock().getId());
        existingOrderDetail.setStock(newStock);

        Order newOrder = orderService.findById(orderDetailDetails.getOrder().getId());
        existingOrderDetail.setOrder(newOrder);

        existingOrderDetail.setQuantity(orderDetailDetails.getQuantity());
    
        calculateSubtotal(existingOrderDetail, newStock.getProduct().getPrice());
        stockService.updateStockQuantity(newStock, orderDetailDetails.getQuantity());
        orderService.calculateTotal(newOrder, existingOrderDetail.getSubtotal());

        return orderDetailRepository.save(existingOrderDetail);
    }

    @Transactional
    public void deleteById(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order detail with id " + id + " not found"));

        Stock stock = orderDetail.getStock();
        stockService.updateStockQuantity(stock, -orderDetail.getQuantity());

        Order order = orderDetail.getOrder();
        orderService.calculateTotal(order, -orderDetail.getSubtotal());

        orderDetailRepository.delete(orderDetail);
    }

    private void calculateSubtotal(OrderDetail orderDetail, double price) {
        double subtotal = orderDetail.getQuantity() * price;
        orderDetail.setSubtotal(subtotal);
    }
}