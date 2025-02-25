package com.developerSNS.backend.domain;

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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId", columnDefinition = "INT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

    @Builder
    public Comment(Board board, User user, String content) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.createdAt = new Date();
    }

    public Comment update(String content) {
        this.content = content;
        this.updatedAt = new Date();
        return this;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
