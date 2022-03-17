package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.UserProduceDto;
import com.example.bebenshop.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CustomMapper {

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "productComments", ignore = true)
    public abstract UserProduceDto toUserProduceDto(UserEntity userEntity);
}
