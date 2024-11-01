package com.owing.entity.domains.universe.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.universe.error.UniverseErrorCode;

public class UniverseException extends OwingException {

    private UniverseException(UniverseErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static UniverseException of(UniverseErrorCode errorCode) {
        return new UniverseException(errorCode, null);
    }

    public static UniverseException of(UniverseErrorCode errorCode, String description) {
        return new UniverseException(errorCode, description);
    }
}
