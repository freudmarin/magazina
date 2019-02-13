package com.dev.magazina.service;

import com.dev.magazina.model.WarehouseProduct;

import java.util.List;

public interface WarehouseProductService {
    void save(WarehouseProduct wp);
    WarehouseProduct getWarehouse(WarehouseProduct wp);
    boolean compare(WarehouseProduct wp);
    List<WarehouseProduct> findAll();
}
//