package com.project.app_login_back.application.service.jwt;

import com.project.app_login_back.application.dto.auth.LoginRequest;
import com.project.app_login_back.domain.models.entity.User;
import com.project.app_login_back.domain.repository.IUserCriteriaRepository;
import com.project.app_login_back.domain.service.IAuthService;
import com.project.app_login_back.insfraestructure.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IAuthServiceImpl implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private IUserCriteriaRepository iUserCriteriaRepository;

    @Autowired
    public IAuthServiceImpl(PasswordEncoder passwordEncoder, IUserCriteriaRepository iUserCriteriaRepository) {
        this.passwordEncoder = passwordEncoder;
        this.iUserCriteriaRepository = iUserCriteriaRepository;
    }

    @Override
    public boolean getUserPassword(LoginRequest loginRequest) {

        String key = ValidationUtil.getIdentifyEmailOrUsername(loginRequest.getIdentifier());
        Map<String, String> map = new HashMap<>();
        map.put(key, loginRequest.getIdentifier());
        map.put("pass", loginRequest.getPassword());

        User user = iUserCriteriaRepository.getConsultUserDifferentCriteria(map)
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(map.get("pass"), user.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        return true;
    }
}
