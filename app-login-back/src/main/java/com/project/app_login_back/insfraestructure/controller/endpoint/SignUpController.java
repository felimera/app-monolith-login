package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.auth.SignUpRequest;
import com.project.app_login_back.application.dto.auth.SignUpResponse;
import com.project.app_login_back.domain.service.ISignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@Tag(name = "Sign up", description = "Sign up operations.")
@RestController
@RequestMapping(path = "/api/v1/signup")
@Slf4j
public class SignUpController {

    private ISignUpService iSignUpService;

    @Autowired
    public SignUpController(ISignUpService iSignUpService) {
        this.iSignUpService = iSignUpService;
    }

    @Operation(
            summary = "Crear usuario en el sistema.",
            description = "Acceso libre",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Datos guardados con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos")
    })
    @PostMapping(path = "/in")
    public ResponseEntity<Object> postSign(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse response = iSignUpService.postSignup(signUpRequest);
        if (Objects.isNull(response.getUser().getCorreo())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Email already exists."));
        } else if (Objects.isNull(response.getUser().getNombreUsuario())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "The username already exists."));
        } else {
            response.setTokenType("Bearer");
            response.setMessage("User successfully registered.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }
}
