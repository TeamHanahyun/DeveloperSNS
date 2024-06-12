package com.developerSNS.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdateRequest {
    @NotBlank(message = "사용자 아이디는 필수입니다. ")
    private String nickname;
}
