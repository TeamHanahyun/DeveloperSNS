package com.developerSNS.backend.dto.request;

import com.developerSNS.backend.domain.Board;
import com.developerSNS.backend.domain.Comment;
import com.developerSNS.backend.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
public class CommentCreateRequest {
    @NotNull(message = "게시글 아이디는 필수입니다. ")
    @JsonProperty(value = "boardId")
    private Long boardId;

    @NotNull(message = "사용자 아이디는 필수입니다. ")
    @JsonProperty(value = "userId")
    private Long userId;

    @NotBlank(message = "댓글 내용을 작성해주세요. ")
    @JsonProperty(value = "content")
    private String content;
}
