package com.project.app_login_back.domain.repository;

import com.project.app_login_back.domain.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u inner join fetch u.rol where u. username = (?1)")
    Optional<User> findByUsername(String username);
}
