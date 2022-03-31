package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.OrderDetailNoteProduceDto;
import com.example.bebenshop.entities.OrderDetailNoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderDetailNoteMapper implements CustomMapper {

    @Mapping(target = "orderDetail", ignore = true)
    public abstract OrderDetailNoteProduceDto toOrderNoteProduceDto(OrderDetailNoteEntity orderNoteEntity);
}
