package com.developerSNS.backend.error.exception.user;

import com.developerSNS.backend.error.ErrorCode;
import com.developerSNS.backend.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
