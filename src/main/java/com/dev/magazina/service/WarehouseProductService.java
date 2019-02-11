package com.dev.magazina.service;

import com.dev.magazina.model.WarehouseProduct;

public interface WarehouseProductService {
    void save(WarehouseProduct wp);

    boolean compare(WarehouseProduct wp);
}
