package com.example.bebenshop.services;

import com.example.bebenshop.dto.produces.CategoryProduceDto;

import java.util.List;

public interface CategoryService {

    List<List<List<CategoryProduceDto>>> getAll(Boolean structure);
}

