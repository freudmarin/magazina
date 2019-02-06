package com.dev.magazina.service;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllByCategory(Category category);
    Product findById(int id);
    Product findByName(String name);
    void save(Product product);
    void delete(Product product);


}
