package com.project.app_login_back.application.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.app_login_back.insfraestructure.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {


    private final Algorithm algorithm = Algorithm.HMAC256(Constants.KEY_TOKEN);

    public String crearToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validez
                .sign(algorithm);
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
}
