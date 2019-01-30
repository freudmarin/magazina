package com.dev.magazina.dao;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    List<Product> findAllByCategory(Category category);

    Product findById(int id);

    void save(Product product);

    void delete(Product product);

}
