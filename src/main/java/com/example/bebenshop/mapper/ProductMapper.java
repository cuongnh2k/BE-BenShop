package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CustomMapper{

    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "categories", ignore = true)
    public abstract ProductProduceDto toProductProduceDto(ProductEntity productEntity);
}
