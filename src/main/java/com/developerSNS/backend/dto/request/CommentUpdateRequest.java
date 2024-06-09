package com.developerSNS.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateRequest {
    @NotBlank(message = "댓글 내용을 작성해주세요. ")
    @JsonProperty("content")
    private String content;
}
