package com.project.app_login_back.application.dto.auth;

import com.project.app_login_back.application.dto.entity.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
    private String token;
    private String tokenType;
    private String message;
    private UserDto user;
}
