package com.dev.magazina.service;

import com.dev.magazina.model.ProductTransactionUnit;

import java.util.List;

public interface ProductTransactionUnitService {
    List<ProductTransactionUnit> findAll();

    ProductTransactionUnit findById(int id);

    void save(ProductTransactionUnit productTransactionUnit);

    void delete(ProductTransactionUnit productTransactinUnit);


}
