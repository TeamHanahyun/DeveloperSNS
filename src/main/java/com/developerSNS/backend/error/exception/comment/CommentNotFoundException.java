package com.developerSNS.backend.error.exception.comment;

import com.developerSNS.backend.error.ErrorCode;
import com.developerSNS.backend.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class CommentNotFoundException extends BusinessException {
    public CommentNotFoundException() {super(ErrorCode.COMMENT_NOT_FOUND);}
}
