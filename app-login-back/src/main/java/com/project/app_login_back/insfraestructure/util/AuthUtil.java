package com.project.app_login_back.insfraestructure.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthUtil {
    private AuthUtil() {
        throw new IllegalStateException(AuthUtil.class.toString());
    }

    public static Map<String, String> getUsernameAndRol() {
        Map<String, String> resultado = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return resultado; // Evita NullPointerException si no hay sesión
        }

        String username = auth.getName();

        // Usamos Collectors.joining para unir los roles en un String real
        String roles = auth.getAuthorities().stream()
                .map(grantedAuthority -> {
                    String authority = grantedAuthority.getAuthority();
                    // Verificamos si empieza con ROLE_ para evitar errores
                    return authority.startsWith("ROLE_") ? authority.substring(5) : authority;
                })
                .collect(Collectors.joining(", ")); // Resultado: "ADMIN, USER"

        resultado.put(username, roles);
        return resultado;
    }
}
