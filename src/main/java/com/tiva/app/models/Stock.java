package com.tiva.app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ProductSize product_size;

    public Stock() {
    }

    public Stock(int id, int quantity, Product product, ProductSize product_size) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.product_size = product_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductSize getProduct_size() {
        return product_size;
    }

    public void setProduct_size(ProductSize product_size) {
        this.product_size = product_size;
    }
}
