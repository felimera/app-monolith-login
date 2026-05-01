package com.project.app_login_back.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.app_login_back.application.dto.auth.LoginRequest;
import com.project.app_login_back.application.dto.auth.LoginResponse;
import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.repository.IUserRepository;
import com.project.app_login_back.insfraestructure.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    private IUserRepository iUserRepository;

    @Autowired
    public JwtService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    private final Algorithm algorithm = Algorithm.HMAC256(Constants.KEY_TOKEN);

    public String crearToken(String username) {
        if (iUserRepository.findByUsername(username).isPresent())
            return JWT.create()
                    .withSubject(username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validez
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
            String token = crearToken(loginRequest.getUsername());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setType("Bearer");
            loginResponse.setEmail(userOptional.get().getEmail());
            loginResponse.setUsername(userOptional.get().getUsername());
            loginResponse.setFullName(userOptional.get().getFirstName().concat(" ").concat(userOptional.get().getLastName()));
            loginResponse.setRoles(userOptional.get().getRol().getName());
            return loginResponse;
        }
        return null;
    }
}
