package com.project.app_login_back.application.mapper;

import com.project.app_login_back.application.dto.entity.UserDto;
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
    @Mapping(target = "telefonoUno", source = "phoneOne")
    @Mapping(target = "telefonoDos", source = "phoneTwo")
    @Mapping(target = "idRol", source = "rol.id")
    @Mapping(target = "codeRole", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    UserDto toDto(User entity);

    @InheritInverseConfiguration
    @Mapping(target = "password", source = "contrasena")
    User toEntity(UserDto dto);
}
