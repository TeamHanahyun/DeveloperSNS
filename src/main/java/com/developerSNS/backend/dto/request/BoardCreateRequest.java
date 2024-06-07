package com.developerSNS.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardCreateRequest {
    @NotBlank(message = "게시물 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "게시물 내용을 작성해주세요.")
    private String content;

    @NotNull(message = "공백일 수 없습니다.")
    private Long userId;
}
