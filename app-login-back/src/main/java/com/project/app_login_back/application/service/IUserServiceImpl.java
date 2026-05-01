package com.project.app_login_back.application.service;

import com.project.app_login_back.application.dto.entity.UserDto;
import com.project.app_login_back.application.mapper.IUserMapper;
import com.project.app_login_back.domain.models.catalog.Rol;
import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.repository.IUserRepository;
import com.project.app_login_back.domain.service.IRolService;
import com.project.app_login_back.domain.service.IUserService;
import com.project.app_login_back.insfraestructure.util.AuthUtil;
import com.project.app_login_back.insfraestructure.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IUserServiceImpl implements IUserService {

    private IUserRepository iUserRepository;
    private IRolService iRolService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IUserServiceImpl(IUserRepository iUserRepository, IRolService iRolService, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.iRolService = iRolService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAll() {
        String rol = AuthUtil.getUsernameAndRol()
                .values()
                .stream().
                findFirst()
                .orElseThrow(() -> new IllegalStateException("The authenticated user has no assigned roles."));
        switch (rol) {
            case Constants.CODE_ROL_CLIENT:
                String username = AuthUtil.getUsernameAndRol()
                        .keySet().stream()
                        .findFirst()
                        .orElse("No information was found.");
                return iUserRepository.getUsersWithRoleClient(rol, username)
                        .stream().map(entity -> {
                            UserDto userDto = IUserMapper.INSTANCE.toDto(entity);
                            userDto.setIdRol(entity.getRol().getId());
                            return userDto;
                        }).toList();
            case Constants.CODE_ROL_DIRECT:
                return iUserRepository.getUsersWithRoleDirector()
                        .stream().map(entity -> {
                            UserDto userDto = IUserMapper.INSTANCE.toDto(entity);
                            userDto.setIdRol(entity.getRol().getId());
                            return userDto;
                        }).toList();
            default:
                return iUserRepository.findAll()
                        .stream().map(entity -> {
                            UserDto userDto = IUserMapper.INSTANCE.toDto(entity);
                            userDto.setIdRol(entity.getRol().getId());
                            return userDto;
                        }).toList();
        }
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        if (iUserRepository.isValidateExistingEmail(userDto.getCorreo())) {
            userDto.setCorreo(null);
            return userDto;
        } else if (iUserRepository.isValidateExistingUsername(userDto.getNombreUsuario())) {
            userDto.setNombreUsuario(null);
            return userDto;
        } else {
            User entity = IUserMapper.INSTANCE.toEntity(userDto);
            String passwordCifrada = passwordEncoder.encode(userDto.getContrasena());
            entity.setPassword(passwordCifrada);
            entity.setRol(iRolService.getTypeRol(Constants.CODE_ROL_CLIENT));
            return IUserMapper.INSTANCE.toDto(iUserRepository.save(entity));
        }
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
