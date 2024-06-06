package com.developerSNS.backend.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
    INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "U001","존재하지 않는 유저 아이디"),

    // BOARD
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "B001","존재하지 않는 게시물"),
    BOARD_USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "P003","승인되지 않은 접근"),

    ;

    private final int httpStatus;
    private final String code;
    private final String message;

}