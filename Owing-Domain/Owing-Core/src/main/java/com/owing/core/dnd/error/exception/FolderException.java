package com.owing.core.dnd.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.core.dnd.error.FolderErrorCode;

public class FolderException extends OwingException {

    private FolderException(FolderErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FolderException of(FolderErrorCode errorCode) {
        return new FolderException(errorCode, null);
    }

    public static FolderException of(FolderErrorCode errorCode, String description) {
        return new FolderException(errorCode, description);
    }
}
