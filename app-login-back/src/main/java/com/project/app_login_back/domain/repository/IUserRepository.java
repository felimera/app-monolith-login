package com.project.app_login_back.domain.repository;

import com.project.app_login_back.domain.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u inner join fetch u.rol where u. username = (?1)")
    Optional<User> findByUsername(String username);

    @Query("select u from User u inner join fetch u.rol where u. email = (?1)")
    Optional<User> findByEmail(String email);

    @Query("select u from User u inner join fetch u.rol r where r.code <> 'ADMIN' ")
    List<User> getUsersWithRoleDirector();

    @Query("select u from User u inner join fetch u.rol r where r.code = (?1) and u.username = (?2)")
    List<User> getUsersWithRoleClient(String rolCliente, String username);

    @Query("select count(u) > 0 from User u where u.email = :email")
    boolean isValidateExistingEmail(@Param("email") String email);

    @Query("select count(u) > 0 from User u where u.username = :username")
    boolean isValidateExistingUsername(@Param("username") String username);
}
