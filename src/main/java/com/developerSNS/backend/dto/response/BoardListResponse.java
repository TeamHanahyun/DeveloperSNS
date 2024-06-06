package com.developerSNS.backend.dto.response;

import com.developerSNS.backend.domain.Board;
import com.developerSNS.backend.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class BoardListResponse {
    private Long id;
    private String title;
    private String content;
    private User user;
    private Date createdAt;

    public static BoardListResponse of(Board board) {
        return BoardListResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .user(board.getUser())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
