package com.project.app_login_back.application.dto.auth;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String tokenType;
    private String username;
    private String email;
    private String fullName;
    private String roles;
}
