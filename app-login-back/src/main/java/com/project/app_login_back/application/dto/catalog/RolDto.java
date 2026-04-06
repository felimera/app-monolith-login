package com.project.app_login_back.application.dto.catalog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RolDto {
    private Long idRol;
    private String codigo;
    private String nombre;
    private String descripcion;
}
