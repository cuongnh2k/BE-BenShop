package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.CategoryProduceDto;
import com.example.bekiashop.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper implements CustomMapper {

    @Mapping(target = "categories1", ignore = true)
    public abstract CategoryProduceDto toCategoryProduceDto(CategoryEntity categoryEntity);
}
