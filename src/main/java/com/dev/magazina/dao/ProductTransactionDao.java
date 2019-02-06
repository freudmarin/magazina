package com.dev.magazina.dao;

import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;

import java.util.List;

public interface ProductTransactionDao {
    List<ProductTransaction> findAll();

    ProductTransaction findById(int id);

    void save(ProductTransaction productTransaction,List<ProductTransactionUnit> ptus);

    void delete(ProductTransaction productTransaction);

}
