package com.project.app_login_back.application.mapper;

import com.project.app_login_back.application.dto.catalog.RolDto;
import com.project.app_login_back.domain.models.catalog.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IRolMapper {
    IRolMapper INSTANCE = Mappers.getMapper(IRolMapper.class);

    @Mapping(target = "idRol", source = "id")
    @Mapping(target = "codigo", source = "code")
    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "descripcion", source = "description")
    RolDto toDto(Rol entity);
}
