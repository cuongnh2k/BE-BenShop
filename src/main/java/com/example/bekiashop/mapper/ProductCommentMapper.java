package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.ProductCommentProduceDto;
import com.example.bekiashop.entities.ProductCommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductCommentMapper implements CustomMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "productComment1", ignore = true)
    public abstract ProductCommentProduceDto toProductCommentProduceDto(ProductCommentEntity productCommentEntity);
}
