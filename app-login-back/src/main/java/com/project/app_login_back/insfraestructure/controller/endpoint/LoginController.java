package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.LoginRequest;
import com.project.app_login_back.application.dto.LoginResponse;
import com.project.app_login_back.application.service.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Tag(name = "Login", description = "Login operations.")
@RestController
@RequestMapping(path = "/api/v1/login")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private JwtService jwtService;

    @Autowired
    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/token")
    public ResponseEntity<Object> loginApi(@RequestBody LoginRequest login) {
        String token = jwtService.crearToken(login.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping(path = "/in")
    public ResponseEntity<Object> login(@RequestBody LoginRequest login) {
        LoginResponse loginResponse = jwtService.login(login);
        // Ejemplo rápido en el Controller
        if (Objects.isNull(loginResponse)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        } else {
            return ResponseEntity.ok(loginResponse);
        }

    }
}
