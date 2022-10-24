package com.example.bekiashop.mapper;

import com.example.bekiashop.dto.produces.RoleProduceDto;
import com.example.bekiashop.entities.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements CustomMapper{

    public abstract RoleProduceDto toRoleProduceDto(RoleEntity roleEntity);
}
