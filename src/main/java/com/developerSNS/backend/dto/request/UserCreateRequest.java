package com.developerSNS.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreateRequest {
    @NotBlank(message = "사용자 이메일은 필수입니다. ")
    private String email;
    @NotBlank(message = "사용자 아이디는 필수입니다. ")
    private String nickname;
    @NotBlank(message = "사용자 비밀번호는 필수입니다. ")
    private String password;
}
