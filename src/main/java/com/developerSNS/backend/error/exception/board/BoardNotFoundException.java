package com.developerSNS.backend.error.exception.board;

import com.developerSNS.backend.error.ErrorCode;
import com.developerSNS.backend.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class BoardNotFoundException extends BusinessException {
    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
