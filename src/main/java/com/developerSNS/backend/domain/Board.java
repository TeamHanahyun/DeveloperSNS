package com.developerSNS.backend.domain;

import com.developerSNS.backend.dto.request.BoardCreateRequest;
import com.developerSNS.backend.dto.request.BoardUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
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

    public static Board of (User user, BoardCreateRequest request) {
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
