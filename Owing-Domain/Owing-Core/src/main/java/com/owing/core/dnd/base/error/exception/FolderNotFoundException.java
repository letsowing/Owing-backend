package com.owing.core.dnd.base.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.core.dnd.base.error.FolderErrorCode;

public class FolderNotFoundException extends OwingException {

    private FolderNotFoundException(FolderErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FolderNotFoundException of(FolderErrorCode errorCode) {
        return new FolderNotFoundException(errorCode, null);
    }

    public static FolderNotFoundException of(FolderErrorCode errorCode, String description) {
        return new FolderNotFoundException(errorCode, description);
    }
}
