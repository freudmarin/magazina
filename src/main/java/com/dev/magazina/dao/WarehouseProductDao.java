package com.dev.magazina.dao;

import com.dev.magazina.model.WarehouseProduct;

import java.util.List;

public interface WarehouseProductDao {
    void save(WarehouseProduct wp);
    boolean compare(WarehouseProduct wp);
    WarehouseProduct getWarehouse(WarehouseProduct wp);
    List<WarehouseProduct> findAll();


}
