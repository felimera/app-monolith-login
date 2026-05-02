package com.project.app_login_back.domain.repository;

import com.project.app_login_back.domain.models.entity.User;

import java.util.Map;
import java.util.Optional;

public interface IUserCriteriaRepository {
    Optional<User> getConsultUserDifferentCriteria(Map<String, String> map);
}
