package com.dev.magazina.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class WarehouseProduct implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;

    @Id
    @ManyToOne
    @JoinColumn
    private Product product;

    private double amount;

    public WarehouseProduct() {
    }

    // e leme keshtu per momentinpo
    public WarehouseProduct(Warehouse warehouse, Product product, double amount) {
        this.warehouse = warehouse;
        this.product = product;
        this.amount = amount;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}