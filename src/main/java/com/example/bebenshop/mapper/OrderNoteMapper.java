package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.OrderNoteProduceDto;
import com.example.bebenshop.entities.OrderNoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderNoteMapper implements CustomMapper{

    @Mapping(target = "order", ignore = true)
    public abstract OrderNoteProduceDto toOrderNoteProduceDto(OrderNoteEntity orderNoteEntity);
}
