package com.owing.core.dnd.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.core.dnd.error.DndErrorCode;

public class DndNotFoundException extends OwingException {

    private DndNotFoundException(DndErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static DndNotFoundException of(DndErrorCode errorCode) {
        return new DndNotFoundException(errorCode, null);
    }

    public static DndNotFoundException of(DndErrorCode errorCode, String description) {
        return new DndNotFoundException(errorCode, description);
    }
}
