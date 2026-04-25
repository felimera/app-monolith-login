package com.project.app_login_back.application.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String type;
    private String username;
    private String email;
    private String fullName;
    private String roles;
}
