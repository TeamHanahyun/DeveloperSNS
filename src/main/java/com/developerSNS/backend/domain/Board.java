package com.developerSNS.backend.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "boardId", columnDefinition = "INT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

    public Board update(BoardUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        return this;
    }
}
