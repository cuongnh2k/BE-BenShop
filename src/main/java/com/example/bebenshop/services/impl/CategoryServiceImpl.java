package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.produces.CategoryProduceDto;
import com.example.bebenshop.entities.CategoryEntity;
import com.example.bebenshop.mapper.CategoryMapper;
import com.example.bebenshop.repository.CategoryRepository;
import com.example.bebenshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository mCategoryRepository;

    private final CategoryMapper mCategoryMapper;

    @Override
    public List<CategoryProduceDto> getAll(){
        List<CategoryEntity> categoryEntityList = mCategoryRepository.findAll();
        return categoryEntityList.stream().map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList());
    }
}
