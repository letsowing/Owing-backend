package com.owing.entity.dnd.base.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.dnd.base.error.DndErrorCode;

public class DndException extends OwingException {

    private DndException(DndErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static DndException of(DndErrorCode errorCode) {
        return new DndException(errorCode, null);
    }

    public static DndException of(DndErrorCode errorCode, String description) {
        return new DndException(errorCode, description);
    }
}
