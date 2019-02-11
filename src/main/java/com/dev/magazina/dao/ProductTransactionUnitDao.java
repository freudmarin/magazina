package com.dev.magazina.dao;

import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;

import java.util.List;

public interface ProductTransactionUnitDao {
    List<ProductTransactionUnit> findAll();

    List<ProductTransactionUnit> findAllByProductTransaction(ProductTransaction pt);

    ProductTransactionUnit findById(int id);

    void save(ProductTransactionUnit productTransactionUnit);

    void delete(ProductTransactionUnit productTransactionUnit);
}
