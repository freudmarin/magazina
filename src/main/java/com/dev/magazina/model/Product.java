package com.dev.magazina.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(unique = true)
    private String name;

    @Size(min = 12, max = 12)
    private String barcode;

    private double price;
    private double amount;

    @ManyToOne
    private MeasuringUnit measuringUnit;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "warehouse_product",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "id"))
    List<Warehouse> warehouses;

    @OneToMany(mappedBy = "product")
    List<WarehouseProduct> warehouseProduct;

    public Product() {
    }

    public Product(String name, String barcode, double price, double amount, MeasuringUnit measuringUnit, Category category) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.amount = amount;
        this.measuringUnit = measuringUnit;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public MeasuringUnit getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(MeasuringUnit measuringUnit) {
        this.measuringUnit = measuringUnit;
    }
}
