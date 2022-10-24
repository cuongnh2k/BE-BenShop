package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.UserProduceDto;
import com.example.bekiashop.entities.UserEntity;
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
