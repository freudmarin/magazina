package com.dev.magazina.service;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;
import com.dev.magazina.model.Warehouse;

import java.util.List;

public interface ProductTransactionService {
    List<ProductTransaction> findAll();

    ProductTransaction findById(int id);

    List<ProductTransaction> findByType(String type, Warehouse warehouse);

    void save(ProductTransaction productTransaction);

    void delete(ProductTransaction productTransactin);
}
