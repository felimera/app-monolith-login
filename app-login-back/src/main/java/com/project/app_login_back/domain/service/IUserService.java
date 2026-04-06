package com.project.app_login_back.domain.service;

import com.project.app_login_back.application.dto.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAll();

    UserDto postUser(UserDto userDto);

    UserDto putUserRol(Long id);
}
