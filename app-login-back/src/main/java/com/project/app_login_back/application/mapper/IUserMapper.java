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
    @Mapping(target = "nombre", source = "firstName")
    @Mapping(target = "apellido", source = "lastName")
    @Mapping(target = "correo", source = "email")
    @Mapping(target = "idRol", source = "rol.id")
    @Mapping(target = "contrasena", source = "password")
    UserDto toDto(User entity);

    @InheritInverseConfiguration
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto dto);
}
