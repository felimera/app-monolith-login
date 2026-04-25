package com.project.app_login_back.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long idUser;
    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String correo;
    private Long idRol;
}
