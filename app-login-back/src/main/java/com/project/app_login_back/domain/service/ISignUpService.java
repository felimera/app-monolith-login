package com.project.app_login_back.domain.service;

import com.project.app_login_back.application.dto.auth.SignUpRequest;
import com.project.app_login_back.application.dto.auth.SignUpResponse;

public interface ISignUpService {
    SignUpResponse postSignup(SignUpRequest signUpRequest);
}
