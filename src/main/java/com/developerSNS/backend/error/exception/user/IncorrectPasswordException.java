package com.developerSNS.backend.error.exception.user;

import com.developerSNS.backend.error.ErrorCode;
import com.developerSNS.backend.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class IncorrectPasswordException extends BusinessException {
    public IncorrectPasswordException() { super(ErrorCode.INCORRECT_PASSWORD);}
}
