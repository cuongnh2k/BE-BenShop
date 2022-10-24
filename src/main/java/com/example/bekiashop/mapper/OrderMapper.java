package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.OrderProduceDto;
import com.example.bekiashop.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderMapper implements CustomMapper{

    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract OrderProduceDto toOrderProduceDto(OrderEntity orderEntity);
}