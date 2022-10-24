package com.example.bekiashop.mapper;


import com.example.bekiashop.dto.produces.DeviceProduceDto;
import com.example.bekiashop.entities.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class DeviceMapper implements CustomMapper {

    @Mapping(target = "user", ignore = true)
    public abstract DeviceProduceDto toDeviceProduceDto(DeviceEntity deviceEntity);
}
