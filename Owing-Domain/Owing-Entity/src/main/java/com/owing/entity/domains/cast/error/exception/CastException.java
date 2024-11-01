package com.owing.entity.domains.cast.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.cast.error.CastErrorCode;

public class CastException extends OwingException {

    private CastException(CastErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static CastException of(CastErrorCode errorCode) {
        return new CastException(errorCode, null);
    }

    public static CastException of(CastErrorCode errorCode, String description) {
        return new CastException(errorCode, description);
    }
}
