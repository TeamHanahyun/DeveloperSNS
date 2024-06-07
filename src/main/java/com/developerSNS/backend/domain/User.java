package com.developerSNS.backend.domain;

import com.developerSNS.backend.dto.request.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = new Date();
    }

    public User update(String nickname) {
        this.nickname = nickname;
        this.updatedAt = new Date();
        return this;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
