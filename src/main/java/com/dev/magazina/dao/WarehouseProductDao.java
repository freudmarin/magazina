package com.dev.magazina.dao;

import com.dev.magazina.model.WarehouseProduct;

public interface WarehouseProductDao {
    void save(WarehouseProduct wp);
    boolean compare(WarehouseProduct wp);
    WarehouseProduct getWarehouse(WarehouseProduct wp);


}
