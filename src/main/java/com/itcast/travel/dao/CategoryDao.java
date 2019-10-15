package com.itcast.travel.dao;

import com.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
