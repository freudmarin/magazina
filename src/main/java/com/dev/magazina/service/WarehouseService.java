package com.dev.magazina.service;

import com.dev.magazina.model.Warehouse;

import java.util.List;

public interface WarehouseService {
    List<Warehouse> findAll();
    Warehouse findById(int id);
    Warehouse findById(Integer id);
    Warehouse findByName(String name);
    Warehouse findFirst();
    void save(Warehouse warehouse);
    void delete(Warehouse warehouse);
}
