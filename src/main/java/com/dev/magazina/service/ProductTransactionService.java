package com.dev.magazina.service;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.ProductTransaction;

import java.util.List;

public interface ProductTransactionService {
    List<ProductTransaction> findAll();
    Category findById(int id);
    void save(ProductTransaction productTransaction);
    void delete(ProductTransaction productTransactin);
}
