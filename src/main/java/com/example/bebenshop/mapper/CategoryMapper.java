package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.CategoryProduceDto;
import com.example.bebenshop.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper implements CustomMapper {

    @Mapping(target = "productCategories", ignore = true)
    public abstract CategoryProduceDto toCategoryProduceDto(CategoryEntity categoryEntity);
}
