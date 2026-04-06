package com.project.app_login_back.application.mapper;

import com.project.app_login_back.application.dto.UserDto;
import com.project.app_login_back.domain.models.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mapping(target = "idUser", source = "id")
    @Mapping(target = "nombreUsuario", source = "username")
    @Mapping(target = "contrasena", source = "password")
    UserDto toDto(User entity);

    @InheritInverseConfiguration
    User toEntity(UserDto dto);
}
