package com.example.bekiashop.services.impl;

import com.example.bekiashop.bases.BaseEntity;
import com.example.bekiashop.dto.consumes.CategoryConsumeDto;
import com.example.bekiashop.dto.produces.CategoryProduce1Dto;
import com.example.bekiashop.dto.produces.CategoryProduce2Dto;
import com.example.bekiashop.dto.produces.CategoryProduceDto;
import com.example.bekiashop.entities.CategoryEntity;
import com.example.bekiashop.exceptions.BadRequestException;
import com.example.bekiashop.mapper.CategoryMapper;
import com.example.bekiashop.repository.CategoryRepository;
import com.example.bekiashop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository mCategoryRepository;
    private final CategoryMapper mCategoryMapper;

    @Override
    public List<CategoryProduceDto> getAll(Boolean structure) {
        List<CategoryProduceDto> categoryProduceDtoList = mCategoryRepository.findAll().stream()
                .map(mCategoryMapper::toCategoryProduceDto).collect(Collectors.toList());
        if (!structure) {
            return categoryProduceDtoList;
        }
        return categoryProduceDtoList.stream()
                .filter(o -> o.getParentId() == 0)
                .map(o ->
                        CategoryProduceDto.builder()
                                .id(o.getId())
                                .createdDate(o.getCreatedDate())
                                .updatedDate(o.getUpdatedDate())
                                .name(o.getName())
                                .parentId(o.getParentId())
                                .categories1(categoryProduceDtoList.stream()
                                        .filter(oo -> o.getId() == oo.getParentId())
                                        .map(oo -> CategoryProduce1Dto.builder()
                                                .id(oo.getId())
                                                .createdDate(oo.getCreatedDate())
                                                .updatedDate(oo.getUpdatedDate())
                                                .name(oo.getName())
                                                .parentId(oo.getParentId())
                                                .categories2(categoryProduceDtoList.stream()
                                                        .filter(ooo -> oo.getId() == ooo.getParentId())
                                                        .map(ooo -> CategoryProduce2Dto.builder()
                                                                .id(ooo.getId())
                                                                .createdDate(ooo.getCreatedDate())
                                                                .updatedDate(ooo.getUpdatedDate())
                                                                .name(ooo.getName())
                                                                .parentId(ooo.getParentId())
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        CategoryEntity categoryEntity = mCategoryRepository.findById(id).orElse(null);
        if (Objects.isNull(categoryEntity)) {
            throw new BadRequestException("Category does not exist");
        }
        List<CategoryEntity> categoryEntityList = mCategoryRepository.findByParentId(id);
        List<Long> list = categoryEntityList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        list.add(id);
        mCategoryRepository.deleteProductCategoryById(list);
        mCategoryRepository.deleteAllById(list);
    }

    @Override
    public CategoryProduceDto addCategory(CategoryConsumeDto categoryConsumeDto) {

        CategoryEntity categoryEntity = categoryConsumeDto.toCategoryEntity();
        if (categoryEntity.getParentId() != null && !mCategoryRepository.existsById(categoryEntity.getParentId())) {
            throw new BadRequestException("Parent category does not exist");
        }
        if (categoryEntity.getParentId() == null) {
            categoryEntity.setParentId(0L);
        }
        if (mCategoryRepository.existsByName(categoryEntity.getName())) {
            throw new BadRequestException("Category already exists");
        }
        return mCategoryMapper.toCategoryProduceDto(mCategoryRepository.save(categoryEntity));
    }

    @Override
    public CategoryProduceDto editById(Long id, HashMap<String, Object> map) {
        CategoryEntity categoryEntity = mCategoryRepository.findById(id).orElse(null);
        if (categoryEntity == null) {
            throw new BadRequestException("Category does not exist");
        }
        for (String i : map.keySet()) {
            switch (i) {
                case "name":
                    String name = map.get(i).toString();
                    CategoryEntity ca = mCategoryRepository.findByName(name);
                    if (ca != null && ca.getId() != id) {
                        throw new BadRequestException(name + "Category already exist");
                    }
                    categoryEntity.setName(name);
                    break;
                case "parentId":
                    Long parentID = Long.parseLong(map.get(i).toString());
                    if (!mCategoryRepository.existsById(parentID)) {
                        throw new BadRequestException("Parent category does not exist");
                    }
                    categoryEntity.setParentId(parentID);
                    break;
            }
        }
        return mCategoryMapper.toCategoryProduceDto(mCategoryRepository.save(categoryEntity));
    }

    @Override
    public CategoryProduceDto getById(Long id) {
        CategoryEntity categoryEntity = mCategoryRepository.findById(id).orElse(null);
        if (Objects.isNull(categoryEntity)) {
            throw new BadRequestException("Category does not exist");
        }
        return mCategoryMapper.toCategoryProduceDto(categoryEntity);
    }
}
