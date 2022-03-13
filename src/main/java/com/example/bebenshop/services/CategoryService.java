package com.example.bebenshop.services;

import com.example.bebenshop.dto.produces.CategoryProduce1Dto;

import java.util.List;

public interface CategoryService {

    List<CategoryProduce1Dto> getAll(Boolean structure);

    void deleteById(Long id);
}

