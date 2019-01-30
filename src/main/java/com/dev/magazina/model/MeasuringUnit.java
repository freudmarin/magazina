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

    public MeasuringUnit() {
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
}
