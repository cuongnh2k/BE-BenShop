package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.OrderDetailProduceDto;
import com.example.bekiashop.entities.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderDetailMapper implements CustomMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "orderDetailNotes", ignore = true)
    public abstract OrderDetailProduceDto toOrderDetailProduceDto(OrderDetailEntity orderDetailEntity);
}
