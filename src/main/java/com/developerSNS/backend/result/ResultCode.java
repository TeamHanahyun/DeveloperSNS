package com.developerSNS.backend.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // BOARD
    BOARD_CREATE_SUCCESS("B001", "게시물 생성 성공"),
    BOARD_UPDATE_SUCCESS("B002", "게시물 수정 성공"),
    BOARD_DELETE_SUCCESS("B003", "게시물 삭제 성공"),
    BOARD_LIST_GET_SUCCESS("B004", "게시물 목록 조회 성공"),
    BOARD_DETAIL_GET_SUCCESS("B005", "게시물 상세 조회 성공"),

    ;

    private final String code;
    private final String message;
}
