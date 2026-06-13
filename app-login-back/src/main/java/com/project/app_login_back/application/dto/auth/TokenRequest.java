package com.project.app_login_back.application.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequest {
    @NotBlank(message = "El nombre es obligatorio.")
    private String username;
    @NotBlank(message = "la contraseña es obligatoria.")
    private String password;
}
