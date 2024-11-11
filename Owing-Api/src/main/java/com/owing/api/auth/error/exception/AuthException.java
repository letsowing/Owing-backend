package com.owing.api.auth.error.exception;

import com.owing.api.auth.error.AuthErrorCode;
import com.owing.common.error.exception.OwingException;

public class AuthException extends OwingException {

    private AuthException(AuthErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static AuthException of(AuthErrorCode errorCode) {
        return new AuthException(errorCode, null);
    }

    public static AuthException of(AuthErrorCode errorCode, String description) {
        return new AuthException(errorCode, description);
    }
}
