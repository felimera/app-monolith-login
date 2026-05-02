package com.project.app_login_back.application.service;

import com.project.app_login_back.application.dto.auth.SignUpRequest;
import com.project.app_login_back.application.dto.auth.SignUpResponse;
import com.project.app_login_back.application.dto.entity.UserDto;
import com.project.app_login_back.application.service.jwt.JwtService;
import com.project.app_login_back.domain.service.ISignUpService;
import com.project.app_login_back.domain.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ISignUpServiceImpl implements ISignUpService {

    private IUserService iUserService;
    private JwtService jwtService;

    @Autowired
    public ISignUpServiceImpl(IUserService iUserService, JwtService jwtService) {
        this.iUserService = iUserService;
        this.jwtService = jwtService;
    }

    @Override
    public SignUpResponse postSignup(SignUpRequest signUpRequest) {
        UserDto userNew = buildUserDtoObject(signUpRequest);
        UserDto dto = iUserService.postUser(userNew);
        String token = jwtService.crearToken(signUpRequest.getNombreUsuario());
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setUser(dto);
        signUpResponse.setToken(token);
        return signUpResponse;
    }

    private static UserDto buildUserDtoObject(SignUpRequest signUpRequest) {
        UserDto userNew = new UserDto();
        userNew.setNombreUsuario(signUpRequest.getNombreUsuario());
        userNew.setCorreo(signUpRequest.getCorreo());
        userNew.setNombre(signUpRequest.getNombre());
        userNew.setApellido(signUpRequest.getApellido());
        userNew.setIdRol(signUpRequest.getIdRol());
        userNew.setContrasena(signUpRequest.getContrasena());
        if (Objects.nonNull(signUpRequest.getTelefonoUno()))
            userNew.setTelefonoUno(signUpRequest.getTelefonoUno());
        if (Objects.nonNull(signUpRequest.getTelefonoDos()))
            userNew.setTelefonoDos(signUpRequest.getTelefonoDos());
        return userNew;
    }
}
