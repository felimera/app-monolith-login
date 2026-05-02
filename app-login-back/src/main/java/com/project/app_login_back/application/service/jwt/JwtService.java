package com.project.app_login_back.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.app_login_back.application.dto.auth.LoginRequest;
import com.project.app_login_back.application.dto.auth.LoginResponse;
import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class JwtService {

    private IUserRepository iUserRepository;
    private Algorithm algorithm;

    @Value("${project.jwt.secret}")
    private String keyToken;
    @Value("${project.jwt.expiration}")
    private Long expirationTime;

    @Autowired
    public JwtService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @PostConstruct
    public void init() {
        if (keyToken == null || keyToken.isEmpty()) {
            throw new IllegalArgumentException("La clave JWT no se cargó correctamente");
        }
        this.algorithm = Algorithm.HMAC256(keyToken);
    }

    public String crearTokenUsername(String username) {
        if (iUserRepository.findByUsername(username).isPresent())
            return JWT.create()
                    .withSubject(username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 1 hora de validez
                    .sign(algorithm);
        else return "";
    }

    public String crearTokenEmail(String email) {
        if (iUserRepository.findByEmail(email).isPresent())
            return JWT.create()
                    .withSubject(email)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 1 hora de validez
                    .sign(algorithm);
        else return "";
    }

    public String validarTokenYObtenerUsuario(String token) {
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = iUserRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            String token = this.getSecurityToken(loginRequest);
            if (Objects.isNull(token))
                return null;
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setTokenType("Bearer");
            loginResponse.setEmail(userOptional.get().getEmail());
            loginResponse.setUsername(userOptional.get().getUsername());
            loginResponse.setFullName(userOptional.get().getFirstName().concat(" ").concat(userOptional.get().getLastName()));
            loginResponse.setRoles(userOptional.get().getRol().getName());
            return loginResponse;
        }
        return null;
    }

    private String getSecurityToken(LoginRequest request) {
        if (Objects.nonNull(request.getUsername()))
            return crearTokenUsername(request.getUsername());
        else if (Objects.nonNull(request.getEmail()))
            return crearTokenEmail(request.getEmail());
        return null;
    }
}
