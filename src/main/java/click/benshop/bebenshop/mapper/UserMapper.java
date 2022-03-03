package click.benshop.bebenshop.mapper;

import click.benshop.bebenshop.dto.produces.UserProduceDto;
import click.benshop.bebenshop.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CustomMapper {

    @Mapping(target = "devices", ignore = true)
    public abstract UserProduceDto toUserProduceDto(UserEntity userEntity);
}
