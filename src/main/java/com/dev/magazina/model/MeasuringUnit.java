package com.dev.magazina.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MeasuringUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String symbol;

    @OneToMany(mappedBy = "measuringUnit")
    private List<Product> products = new ArrayList<>();


    public MeasuringUnit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public MeasuringUnit(String symbol) {
        this.symbol = symbol;

    }

    public void setId(int id) {
        this.id = id;
    }

    public MeasuringUnit() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public MeasuringUnit(MeasuringUnit.MeasuringUnitBuilder builder) {
        this.name = builder.name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static class MeasuringUnitBuilder {
        private String name;

        public MeasuringUnitBuilder(String name) {
            this.name = name;
        }

        public MeasuringUnit.MeasuringUnitBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MeasuringUnit build() {
            return new MeasuringUnit(this);
        }
    }
}
