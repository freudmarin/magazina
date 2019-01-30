package com.dev.magazina.dao;

import com.dev.magazina.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(int id);
    void save(Category category);
    void delete(Category category);
}
