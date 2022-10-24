package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.ProductImageProduceDto;
import com.example.bekiashop.entities.ProductImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductImageMapper implements CustomMapper{

    @Mapping(target = "product", ignore = true)
    public abstract ProductImageProduceDto toProductImageProduceDto(ProductImageEntity productImageEntity);
}
