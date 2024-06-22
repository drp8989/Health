package com.JustHealth.Health.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "admin_user")
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "admin_name",unique = true, nullable = false)
    private String adminName;

    @Column(name = "admin_username")
    private String adminUserName;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_password")
    private String adminPassword;

//    will use later
    @Enumerated(EnumType.STRING)
    private ADMIN_ROLE role;

//    @Column(name = "role")
//    private String role;




}
