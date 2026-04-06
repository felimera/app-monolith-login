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
    @Column(name = "us_password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_ro_id")
    private Rol rol;
}
