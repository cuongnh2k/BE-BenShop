package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.produces.CategoryProduce1Dto;
import com.example.bebenshop.dto.produces.CategoryProduce2Dto;
import com.example.bebenshop.dto.produces.CategoryProduce3Dto;
import com.example.bebenshop.dto.produces.CategoryProduceDto;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository mCategoryRepository;
    private final CategoryMapper mCategoryMapper;

    @Override
    public List<CategoryProduce1Dto> getAll(Boolean structure) {
        List<CategoryProduceDto> categoryProduceDtoList = mCategoryRepository.findAll().stream()
                .map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList());
        if (!structure) {
            return categoryProduceDtoList.stream().map(o ->
                    CategoryProduce1Dto.builder()
                            .id(o.getId())
                            .createdDate(o.getCreatedDate())
                            .updatedDate(o.getUpdatedDate())
                            .name(o.getName())
                            .parentId(o.getParentId())
                            .build()).collect(Collectors.toList());
        }
        return categoryProduceDtoList.stream()
                .filter(o -> o.getParentId() == 0)
                .map(o ->
                        CategoryProduce1Dto.builder()
                                .id(o.getId())
                                .createdDate(o.getCreatedDate())
                                .updatedDate(o.getUpdatedDate())
                                .name(o.getName())
                                .parentId(o.getParentId())
                                .categories2(categoryProduceDtoList.stream()
                                        .filter(oo -> o.getId() == oo.getParentId())
                                        .map(oo -> CategoryProduce2Dto.builder()
                                                .id(oo.getId())
                                                .createdDate(oo.getCreatedDate())
                                                .updatedDate(oo.getUpdatedDate())
                                                .name(oo.getName())
                                                .parentId(oo.getParentId())
                                                .categories3(categoryProduceDtoList.stream()
                                                        .filter(ooo -> oo.getId() == ooo.getParentId())
                                                        .map(ooo -> CategoryProduce3Dto.builder()
                                                                .id(ooo.getId())
                                                                .createdDate(ooo.getCreatedDate())
                                                                .updatedDate(ooo.getUpdatedDate())
                                                                .name(ooo.getName())
                                                                .parentId(ooo.getParentId())
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList());
    }
}
