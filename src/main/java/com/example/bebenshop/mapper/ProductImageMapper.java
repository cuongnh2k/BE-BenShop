package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.ProductImageProduceDto;
import com.example.bebenshop.entities.ProductImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductImageMapper implements CustomMapper{

    @Mapping(target = "product", ignore = true)
    public abstract ProductImageProduceDto toProductImageProduceDto(ProductImageEntity productImageEntity);
}
