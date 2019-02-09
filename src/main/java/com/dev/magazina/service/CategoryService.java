package com.dev.magazina.service;

import com.dev.magazina.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(int id);
    Category findByName(String name);
    void save(Category category);
    void delete(Category category);
}
