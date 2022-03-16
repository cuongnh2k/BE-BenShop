package com.example.bebenshop.mapper;

import com.example.bebenshop.dto.produces.RoleProduceDto;
import com.example.bebenshop.entities.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements CustomMapper{

    public abstract RoleProduceDto toRoleProduceDto(RoleEntity roleEntity);
}
