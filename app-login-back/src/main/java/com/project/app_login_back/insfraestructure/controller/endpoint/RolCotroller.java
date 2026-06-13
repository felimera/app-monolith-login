package com.project.app_login_back.insfraestructure.controller.endpoint;

import com.project.app_login_back.domain.service.IRolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rol", description = "Rol operations.")
@RestController
@RequestMapping(path = "/api/v1/rol")
@Slf4j
public class RolCotroller {
    private IRolService iRolService;

    @Autowired
    public RolCotroller(IRolService iRolService) {
        this.iRolService = iRolService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(iRolService.getAll());
    }
}
