package com.project.app_login_back.domain.models.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "cat_rol")
public class Rol {
    @Id
    @Column(name = "ro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ro_code")
    private String code;
    @Column(name = "ro_name")
    private String name;
    @Column(name = "ro_description")
    private String description;
    @Column(name = "ro_valid")
    private Boolean valid;
    @Column(name = "ro_visible")
    private Boolean visible;
}