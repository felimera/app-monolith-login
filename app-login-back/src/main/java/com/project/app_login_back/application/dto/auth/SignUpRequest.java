package com.project.app_login_back.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Username is required.")
    private String nombreUsuario;
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String contrasena;
    @NotBlank(message = "Name is required.")
    private String nombre;
    @NotBlank(message = "The surname is mandatory.")
    private String apellido;
    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String correo;
    private String telefonoUno;
    private String telefonoDos;
    private Long idRol;
}
