package com.owing.core.dnd.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.core.dnd.error.DndErrorCode;

public class DndInvalidPositionException extends OwingException {

    private DndInvalidPositionException(DndErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static DndInvalidPositionException of(DndErrorCode errorCode) {
        return new DndInvalidPositionException(errorCode, null);
    }

    public static DndInvalidPositionException of(DndErrorCode errorCode, String description) {
        return new DndInvalidPositionException(errorCode, description);
    }
}
