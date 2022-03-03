package click.benshop.bebenshop.mapper;


import click.benshop.bebenshop.dto.produces.DeviceProduceDto;
import click.benshop.bebenshop.entities.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class DeviceMapper implements CustomMapper {

    @Mapping(target = "user", ignore = true)
    public abstract DeviceProduceDto toDeviceProduceDto(DeviceEntity deviceEntity);
}
