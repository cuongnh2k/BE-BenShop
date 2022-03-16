package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.OrderDetailProduceDto;
import com.example.bebenshop.entities.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderDetailMapper implements CustomMapper{

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "money", ignore = true)
    public abstract OrderDetailProduceDto toOrderDetailProduceDto(OrderDetailEntity orderDetailEntity);
}
