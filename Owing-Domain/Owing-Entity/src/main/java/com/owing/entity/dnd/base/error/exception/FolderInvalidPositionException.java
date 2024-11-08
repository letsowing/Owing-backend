package com.owing.entity.dnd.base.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.dnd.base.error.FolderErrorCode;

public class FolderInvalidPositionException extends OwingException {

    private FolderInvalidPositionException(FolderErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FolderInvalidPositionException of(FolderErrorCode errorCode) {
        return new FolderInvalidPositionException(errorCode, null);
    }

    public static FolderInvalidPositionException of(FolderErrorCode errorCode, String description) {
        return new FolderInvalidPositionException(errorCode, description);
    }
}
