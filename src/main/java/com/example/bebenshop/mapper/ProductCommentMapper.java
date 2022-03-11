package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.ProductCommentProduceDto;
import com.example.bebenshop.entities.ProductCommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductCommentMapper implements CustomMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract ProductCommentProduceDto toProductCommentProduceDto(ProductCommentEntity productCommentEntity);
}
