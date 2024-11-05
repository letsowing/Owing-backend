package com.owing.entity.domains.universe.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.universe.error.UniverseErrorCode;

public class UniverseNotFoundException extends OwingException {

    private UniverseNotFoundException(UniverseErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static UniverseNotFoundException of(UniverseErrorCode errorCode) {
        return new UniverseNotFoundException(errorCode, null);
    }

    public static UniverseNotFoundException of(UniverseErrorCode errorCode, String description) {
        return new UniverseNotFoundException(errorCode, description);
    }
}
