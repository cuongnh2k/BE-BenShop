package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.CategoryConsumeDto;
import com.example.bebenshop.dto.produces.CategoryProduce1Dto;
import com.example.bebenshop.dto.produces.CategoryProduceDto;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {

    List<CategoryProduce1Dto> getAll(Boolean structure);

    void deleteById(Long id);


    CategoryProduceDto addCategory(CategoryConsumeDto categoryConsumeDto);



    CategoryProduceDto editById(Long id, HashMap<String, Object> map);

    CategoryProduceDto getById(Long id);
}