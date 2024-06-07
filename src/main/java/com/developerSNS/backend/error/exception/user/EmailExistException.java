package com.developerSNS.backend.error.exception.user;

import com.developerSNS.backend.error.ErrorCode;
import com.developerSNS.backend.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class EmailExistException extends BusinessException {
    public EmailExistException() { super(ErrorCode.EXISTS_EMAIL);}
}
