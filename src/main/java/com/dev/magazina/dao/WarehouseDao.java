package com.dev.magazina.dao;

import com.dev.magazina.model.Warehouse;

import java.util.List;

public interface WarehouseDao {
    List<Warehouse> findAll();
    Warehouse findById(int id);
    Warehouse findByName(String name);
    Warehouse findFirst();
    void save(Warehouse warehouse);
    void delete(Warehouse warehouse);
}
