package com.dev.magazina.dao;

import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.Warehouse;

import java.util.List;

public interface ProductTransactionDao {
    List<ProductTransaction> findAll();

    List<ProductTransaction> findByType(String type, Warehouse warehouse);

    ProductTransaction findById(int id);

    void save(ProductTransaction productTransaction);

    void delete(ProductTransaction productTransaction);

}
