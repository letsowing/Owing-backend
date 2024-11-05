package com.owing.api.auth.error.exception;

import com.owing.api.auth.error.AuthErrorCode;
import com.owing.common.error.exception.OwingException;

public class AuthInvalidTokenException extends OwingException  {

    private AuthInvalidTokenException(AuthErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static AuthInvalidTokenException of(AuthErrorCode errorCode) {
        return new AuthInvalidTokenException(errorCode, errorCode.getCode());
    }

    public static AuthInvalidTokenException of(AuthErrorCode errorCode, String description) {
        return new AuthInvalidTokenException(errorCode, description);
    }
}
