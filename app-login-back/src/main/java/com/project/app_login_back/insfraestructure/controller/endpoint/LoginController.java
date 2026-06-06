package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.auth.LoginRequest;
import com.project.app_login_back.application.dto.auth.LoginResponse;
import com.project.app_login_back.application.dto.auth.TokenRequest;
import com.project.app_login_back.application.service.jwt.JwtService;
import com.project.app_login_back.domain.service.IAuthService;
import com.project.app_login_back.insfraestructure.util.Constants;
import com.project.app_login_back.insfraestructure.util.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Tag(name = "login")
@RestController
@RequestMapping(path = "/api/v1/login")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    JwtService jwtService;
    IAuthService iAuthService;

    @Autowired
    public LoginController(JwtService jwtService, IAuthService iAuthService) {
        this.jwtService = jwtService;
        this.iAuthService = iAuthService;
    }

    @PostMapping(path = "/token")
    @Operation(
            operationId = "loginToken", // Este es el ID único para este endpoint
            summary = "loginToken.summary", // Opcional: puedes dejar pistas aquí
            description = "loginToken.description"
    )
    public ResponseEntity<Object> loginApi(@Valid @RequestBody TokenRequest tokenRequest) {
        LoginRequest login = new LoginRequest(tokenRequest.getUsername(), tokenRequest.getPassword());
        boolean isVerificado = iAuthService.getUserPassword(login);
        if (isVerificado) {
            String token = jwtService.crearTokenUsername(tokenRequest.getUsername());
            String mensajeToken = MessageUtils.getMessage(Constants.CONFIG_TOKEN);
            return ResponseEntity.ok(Map.of(mensajeToken, token));
        } else {
            String mensaje = MessageUtils.getMessage(Constants.CONFIG_MESSAGE);
            String mensajeCredencialUserPass = MessageUtils.getMessage(Constants.MESSAGE_CREDENTIALS_USERPASS);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(mensaje, mensajeCredencialUserPass));
        }
    }

    @PostMapping(path = "/in")
    @Operation(
            operationId = "loginRequest", // Este es el ID único para este endpoint
            summary = "loginRequest.summary", // Opcional: puedes dejar pistas aquí
            description = "loginRequest.description"
    )
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest login) {
        boolean isVerificado = iAuthService.getUserPassword(login);
        if (isVerificado) {
            LoginResponse loginResponse = jwtService.login(login);
            // Ejemplo rápido en el Controller
            if (Objects.isNull(loginResponse)) {
                String mensaje = MessageUtils.getMessage(Constants.CONFIG_MESSAGE);
                String mensajeCredencialUserPass = MessageUtils.getMessage(Constants.MESSAGE_CREDENTIALS_USERPASS);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(mensaje, mensajeCredencialUserPass));
            } else {
                return ResponseEntity.ok(loginResponse);
            }
        } else {
            String mensaje = MessageUtils.getMessage(Constants.CONFIG_MESSAGE);
            String mensajeCredencialUserPass = MessageUtils.getMessage(Constants.MESSAGE_CREDENTIALS_USERPASS);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(mensaje, mensajeCredencialUserPass));
        }
    }
}
