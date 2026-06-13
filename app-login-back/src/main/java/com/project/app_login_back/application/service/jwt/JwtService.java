package com.project.app_login_back.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.app_login_back.application.dto.auth.LoginRequest;
import com.project.app_login_back.application.dto.auth.LoginResponse;
import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.repository.IUserCriteriaRepository;
import com.project.app_login_back.domain.repository.IUserRepository;
import com.project.app_login_back.insfraestructure.util.ValidationUtil;
import com.project.app_login_back.insfraestructure.util.Constants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
    private IUserRepository iUserRepository;
    private IUserCriteriaRepository iUserCriteriaRepository;
    private Algorithm algorithm;

    @Value("${project.jwt.secret}")
    private String keyToken;
    @Value("${project.jwt.expiration}")
    private Long expirationTime;

    @Autowired
    public JwtService(IUserRepository iUserRepository, IUserCriteriaRepository iUserCriteriaRepository) {
        this.iUserRepository = iUserRepository;
        this.iUserCriteriaRepository = iUserCriteriaRepository;
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
        String key = ValidationUtil.getIdentifyEmailOrUsername(loginRequest.getIdentifier());
        Map<String, String> map = new HashMap<>();
        map.put(key, loginRequest.getIdentifier());
        map.put("pass", loginRequest.getPassword());

        User user = iUserCriteriaRepository.getConsultUserDifferentCriteria(map)
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));

        String token = this.getSecurityToken(loginRequest);
        if (Objects.isNull(token))
            return null;
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setTokenType("Bearer");
        loginResponse.setEmail(user.getEmail());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setFullName(user.getFirstName().concat(" ").concat(user.getLastName()));
        loginResponse.setRoles(user.getRol().getName());
        loginResponse.setCodeRole(user.getRol().getCode());
        return loginResponse;
    }


    private String getSecurityToken(LoginRequest request) {
        String key = ValidationUtil.getIdentifyEmailOrUsername(request.getIdentifier());
        if (Constants.U.equals(key))
            return crearTokenUsername(request.getIdentifier());
        else if (Constants.E.equals(key))
            return crearTokenEmail(request.getIdentifier());
        return null;
    }
}
