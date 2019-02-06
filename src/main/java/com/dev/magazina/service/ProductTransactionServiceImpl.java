package com.dev.magazina.service;

import com.dev.magazina.dao.ProductTransactionDao;
import com.dev.magazina.model.Category;
import com.dev.magazina.model.ProductTransaction;
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
    public Category findById(int id) {
        return null;
    }

    @Override
    public void save(ProductTransaction productTransaction) {
        System.out.println("jj");
//        productTransactionDao.save(productTransaction);
    }

    @Override
    public void delete(ProductTransaction productTransaction) {

    }
}
