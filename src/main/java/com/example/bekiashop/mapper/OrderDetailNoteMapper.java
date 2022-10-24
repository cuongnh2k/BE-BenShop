package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.OrderDetailNoteProduceDto;
import com.example.bekiashop.entities.OrderDetailNoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderDetailNoteMapper implements CustomMapper {

    @Mapping(target = "orderDetail", ignore = true)
    public abstract OrderDetailNoteProduceDto toOrderNoteProduceDto(OrderDetailNoteEntity orderNoteEntity);
}
