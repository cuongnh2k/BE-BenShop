package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.ProductCategoryProduceDto;
import com.example.bebenshop.entities.ProductCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductCategoryMapper implements CustomMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "product", ignore = true)
    public abstract ProductCategoryProduceDto toProductCategoryProduceDto(ProductCategoryEntity productCategoryEntity);
}
