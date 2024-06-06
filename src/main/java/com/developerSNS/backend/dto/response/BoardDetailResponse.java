package com.developerSNS.backend.dto.response;

import com.developerSNS.backend.domain.Board;
import com.developerSNS.backend.domain.Comment;
import com.developerSNS.backend.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class BoardDetailResponse {
    private Long id;
    private String title;
    private String content;
    private User user;
    private Date createdAt;
    private Date updatedAt;
    private List<CommentResponse> commentResponse;

    public static BoardDetailResponse of(Board board, List<CommentResponse> commentResponse) {
        return BoardDetailResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .user(board.getUser())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .commentResponse(commentResponse)
                .build();
    }
}
