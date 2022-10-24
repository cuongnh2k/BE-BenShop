package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.ProductProduceDto;
import com.example.bekiashop.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CustomMapper {

    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "productComments", ignore = true)
    public abstract ProductProduceDto toProductProduceDto(ProductEntity productEntity);
}
