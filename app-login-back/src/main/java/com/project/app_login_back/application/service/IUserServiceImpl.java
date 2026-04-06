package com.project.app_login_back.application.service;

import com.project.app_login_back.application.dto.UserDto;
import com.project.app_login_back.application.mapper.IUserMapper;
import com.project.app_login_back.domain.models.catalog.Rol;
import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.repository.IUserRepository;
import com.project.app_login_back.domain.service.IRolService;
import com.project.app_login_back.domain.service.IUserService;
import com.project.app_login_back.insfraestructure.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IUserServiceImpl implements IUserService {

    private IUserRepository iUserRepository;
    private IRolService iRolService;

    @Autowired
    public IUserServiceImpl(IUserRepository iUserRepository, IRolService iRolService) {
        this.iUserRepository = iUserRepository;
        this.iRolService = iRolService;
    }

    @Override
    public List<UserDto> getAll() {
        return iUserRepository.findAll().stream().map(entity -> {
            UserDto userDto = IUserMapper.INSTANCE.toDto(entity);
            userDto.setIdRol(entity.getRol().getId());
            return userDto;
        }).toList();
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        User entity = IUserMapper.INSTANCE.toEntity(userDto);
        entity.setRol(iRolService.getTypeRol(Constants.CODE_ROL_CLIENT));
        return IUserMapper.INSTANCE.toDto(iUserRepository.save(entity));
    }

    @Override
    public UserDto putUserRol(Long id) {
        Optional<User> userOldOptional = iUserRepository.findById(id);
        if (userOldOptional.isPresent()) {
            Rol rol = iRolService.getTypeRol(Constants.CODE_ROL_ADMIN);
            userOldOptional.get().setRol(rol);
            User userNew = iUserRepository.save(userOldOptional.get());
            UserDto userDto = IUserMapper.INSTANCE.toDto(userNew);
            userDto.setIdRol(rol.getId());
            return userDto;
        }
        return null;
    }
}
