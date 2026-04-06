package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.application.dto.UserDto;
import com.project.app_login_back.domain.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping(path = "/updaterol/{id}")
    public ResponseEntity<UserDto> putUserRol(@PathVariable(name = "id") Long id) {
        UserDto dto = iUserService.putUserRol(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
