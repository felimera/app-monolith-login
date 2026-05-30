package com.project.app_login_back.application.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Identifier is required.")
    private String identifier;
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;
}
