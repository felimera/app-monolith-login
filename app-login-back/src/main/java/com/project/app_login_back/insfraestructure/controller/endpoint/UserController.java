package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.entity.UserDto;
import com.project.app_login_back.domain.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = iUserService.getAll();
        return ResponseEntity.ok(userDtos);
    }

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

    @PutMapping(path = "/updaterol/{id}")
    public ResponseEntity<UserDto> putUserRol(@PathVariable(name = "id") Long id) {
        UserDto dto = iUserService.putUserRol(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
