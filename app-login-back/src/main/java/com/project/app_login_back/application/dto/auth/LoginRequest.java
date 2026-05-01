package com.project.app_login_back.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Name is required.")
    private String username;
    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;
}
