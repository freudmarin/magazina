package com.dev.magazina.service;

import com.dev.magazina.dao.ProductTransactionDao;
import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTransactionServiceImpl implements ProductTransactionService {
    @Autowired
    private ProductTransactionDao productTransactionDao;

    @Override
    public List<ProductTransaction> findAll() {
        return productTransactionDao.findAll();
    }

    @Override
    public ProductTransaction findById(int id) {
        return productTransactionDao.findById(id);
    }

    @Override
    public List<ProductTransaction> findByType(String type, Warehouse warehouse) {
        return productTransactionDao.findByType(type, warehouse);
    }

    @Override
    public void save(ProductTransaction productTransaction) {
        productTransactionDao.save(productTransaction);
    }

    @Override
    public void delete(ProductTransaction productTransaction) {
        productTransactionDao.delete(productTransaction);
    }
}
