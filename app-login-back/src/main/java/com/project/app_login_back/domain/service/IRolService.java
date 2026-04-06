package com.project.app_login_back.domain.service;

import com.project.app_login_back.application.dto.catalog.RolDto;
import com.project.app_login_back.domain.models.catalog.Rol;

import java.util.List;

public interface IRolService {
    List<RolDto> getAll();

    Rol getTypeRol(String typeRol);
}
