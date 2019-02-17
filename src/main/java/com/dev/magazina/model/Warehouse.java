package com.dev.magazina.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String name;

    @NotNull
    @Size(min = 3, max = 100)
    private String city;

    @NotNull
    @Size(min = 3, max = 100)
    private String address;

    @NotNull
    @Size(min = 3, max = 100)
    private String image;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    List<WarehouseProduct> warehouseProduct;

    public Warehouse() {
    }

    public Warehouse(@NotNull @Size(min = 3, max = 50) String name,
                     @NotNull @Size(min = 3, max = 100) String city,
                     @NotNull @Size(min = 3, max = 100) String address,
                     @NotNull @Size(min = 3, max = 100) String image) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.image = image;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
