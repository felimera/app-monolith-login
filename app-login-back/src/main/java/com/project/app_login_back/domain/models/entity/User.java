package com.project.app_login_back.domain.models.entity;

import com.project.app_login_back.domain.models.catalog.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_id")
    private Long id;
    @Column(name = "us_username", unique = true, columnDefinition = "TEXT")
    private String username;
    @Column(name = "us_email", unique = true, columnDefinition = "TEXT")
    private String email;
    @Column(name = "us_first_name", unique = true, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "us_last_name", unique = true, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "us_password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @Column(name = "us_phone_1", length = 20)
    private String phoneOne;
    @Column(name = "us_phone_2", length = 20)
    private String phoneTwo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_ro_id")
    private Rol rol;
}
