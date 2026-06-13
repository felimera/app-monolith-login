package com.project.app_login_back.domain.service;

import com.project.app_login_back.application.dto.auth.LoginRequest;

public interface IAuthService {
    boolean getUserPassword(LoginRequest request);
}
