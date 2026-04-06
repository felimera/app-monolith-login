package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.LoginRequest;
import com.project.app_login_back.application.service.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Login", description = "Login operations.")
@RestController
@RequestMapping(path = "/api/v1/logintoken")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private JwtService jwtService;

    @Autowired
    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Object> loginApi(@RequestBody LoginRequest login) {
        String token = jwtService.crearToken(login.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }

}
