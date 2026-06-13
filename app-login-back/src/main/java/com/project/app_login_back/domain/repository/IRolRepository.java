package com.project.app_login_back.domain.repository;

import com.project.app_login_back.domain.models.catalog.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByCode(String code);
}
