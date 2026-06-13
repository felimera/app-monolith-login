package com.project.app_login_back.application.service;

import com.project.app_login_back.application.dto.catalog.RolDto;
import com.project.app_login_back.application.mapper.IRolMapper;
import com.project.app_login_back.domain.models.catalog.Rol;
import com.project.app_login_back.domain.repository.IRolRepository;
import com.project.app_login_back.domain.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRolServiceImpl implements IRolService {

    private IRolRepository iRolRepository;

    @Autowired
    public IRolServiceImpl(IRolRepository iRolRepository) {
        this.iRolRepository = iRolRepository;
    }

    @Override
    public List<RolDto> getAll() {
        return iRolRepository.findAll().stream().map(IRolMapper.INSTANCE::toDto).toList();
    }

    @Override
    public Rol getTypeRol(String typeRol) {
        return iRolRepository.findByCode(typeRol).orElseThrow();
    }
}
