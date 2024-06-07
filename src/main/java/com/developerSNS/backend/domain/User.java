package com.developerSNS.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", columnDefinition = "INT")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;
}
