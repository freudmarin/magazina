package com.dev.magazina.dao;

import com.dev.magazina.model.WarehouseProduct;

import java.util.List;

public interface WarehouseProductDao {
    //    List<WarehouseProduct> findAll();
//    WarehouseProduct findById(int id);
//    WarehouseProduct findByName(String name);
    void save(WarehouseProduct wp);
    boolean compare(WarehouseProduct wp);
//    void delete(WarehouseProduct wp);


}
