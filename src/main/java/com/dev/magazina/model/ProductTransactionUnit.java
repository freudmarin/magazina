package com.dev.magazina.model;

import javax.persistence.*;

@Entity(name = "product_transaction_units")
public class ProductTransactionUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Product product;
    private double amount;
    private double price;

    @ManyToOne
    private ProductTransaction productTransaction;

    public ProductTransactionUnit(Product product, double amount, double price, ProductTransaction productTransaction) {
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.productTransaction = productTransaction;
    }

    public ProductTransactionUnit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductTransaction getProductTransaction() {
        return productTransaction;
    }

    public void setProductTransaction(ProductTransaction productTransaction) {
        this.productTransaction = productTransaction;
    }
}
