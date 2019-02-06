package com.dev.magazina.service;

import com.dev.magazina.dao.ProductTransactionUnitDao;
import com.dev.magazina.model.ProductTransactionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTransactionUnitServiceImpl implements ProductTransactionUnitService {

    @Autowired
    private ProductTransactionUnitDao productTransactionUnitDao;


    @Override
    public List<ProductTransactionUnit> findAll() {
        return null;
    }

    @Override
    public ProductTransactionUnit findById(int id) {
        return null;
    }

    @Override
    public void save(ProductTransactionUnit productTransactionUnit) {

    }

    @Override
    public void delete(ProductTransactionUnit productTransactinUnit) {

    }



}
