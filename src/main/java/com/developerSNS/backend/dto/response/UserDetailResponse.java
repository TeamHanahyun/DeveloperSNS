package com.developerSNS.backend.dto.response;

import com.developerSNS.backend.domain.User;
import lombok.Getter;

@Getter
public class UserDetailResponse {
    private String email;
    private String nickname;

    public UserDetailResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
