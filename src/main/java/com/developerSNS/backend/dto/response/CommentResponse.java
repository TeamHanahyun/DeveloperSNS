package com.developerSNS.backend.dto.response;

import com.developerSNS.backend.domain.Comment;
import com.developerSNS.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private Long userId;
    private Date createdAt;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
