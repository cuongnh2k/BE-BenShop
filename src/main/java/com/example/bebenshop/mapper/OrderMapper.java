package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.OrderProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderMapper implements CustomMapper{

    @Mapping(target = "orderNotes", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "totalMoney", ignore = true)
    public abstract OrderProduceDto toOrderProduceDto(OrderEntity orderEntity);
}