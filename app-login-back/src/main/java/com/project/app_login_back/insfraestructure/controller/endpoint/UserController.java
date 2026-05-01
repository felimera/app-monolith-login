package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.entity.UserDto;
import com.project.app_login_back.domain.service.IUserService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "User", description = "User operations.")
@RestController
@RequestMapping(path = "/api/v1/user")
@Slf4j
public class UserController {

    private IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Operation(
            summary = "Obtener la lista de usuarios.",
            description = "Acceso para personal autorizado. Roles permitidos: [ADMIN, DIRECT, CLIENT]",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: No posees uno de los roles requeridos")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECT', 'CLIENT')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = iUserService.getAll();
        return ResponseEntity.ok(userDtos);
    }

    @Operation(
            summary = "Crear usuario en el sistema.",
            description = "Acceso restringido. Requiere rol: [ADMIN]",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Datos guardados con éxito"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos (Se requiere ADMIN)")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // <--- Seguridad Real
    @PostMapping(path = "/in")
    public ResponseEntity<Object> postUser(@Valid @RequestBody UserDto userDto) {
        UserDto dto = iUserService.postUser(userDto);
        if (Objects.isNull(dto.getCorreo())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists.");
        } else if (Objects.isNull(dto.getNombreUsuario())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The username already exists.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
    }

    @Operation(
            summary = "Modificar el rol del usuario.",
            description = "Acceso restringido. Requiere rol: [ADMIN]",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Modificacion del rol éxitosa"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos (Se requiere ROLE_ADMIN)")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/updaterol/{id}")
    public ResponseEntity<UserDto> putUserRol(@PathVariable(name = "id") Long id) {
        UserDto dto = iUserService.putUserRol(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
