package com.dev.magazina.service;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;

import java.util.List;

public interface ProductTransactionService {
    List<ProductTransaction> findAll();

    Category findById(int id);

    List<ProductTransaction> findByType(String type);

    void save(ProductTransaction productTransaction);

    void delete(ProductTransaction productTransactin);
}
