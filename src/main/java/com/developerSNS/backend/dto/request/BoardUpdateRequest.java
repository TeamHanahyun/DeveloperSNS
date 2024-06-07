package com.developerSNS.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardUpdateRequest {

    @NotBlank(message = "게시물 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "게시물 내용을 작성해주세요.")
    private String content;
}
