package com.developerSNS.backend.domain;

import com.developerSNS.backend.dto.request.BoardCreateRequest;
import com.developerSNS.backend.dto.request.BoardUpdateRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId", columnDefinition = "INT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String category;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public Board() {
    }

    @Builder
    public Board(Long id, User user, String title, String content, String category, Date createdAt, Date updatedAt, Boolean isDeleted) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public static Board of(User user, BoardCreateRequest request) {
        return Board.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(new Date())
                .isDeleted(false)
                .build();
    }

    public Board update(BoardUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.updatedAt = new Date();
        return this;
    }
}
