package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.UserCodeProduceDto;
import com.example.bebenshop.entities.UserCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserCodeMapper implements CustomMapper {

    @Mapping(target = "user", ignore = true)
    public abstract UserCodeProduceDto toUserCodeProduceDto(UserCodeEntity userCodeEntity);
}