package com.owing.entity.dnd.base.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.dnd.base.error.FileErrorCode;

public class FileInvalidPositionException extends OwingException {

    private FileInvalidPositionException(FileErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FileInvalidPositionException of(FileErrorCode errorCode) {
        return new FileInvalidPositionException(errorCode, null);
    }

    public static FileInvalidPositionException of(FileErrorCode errorCode, String description) {
        return new FileInvalidPositionException(errorCode, description);
    }
}
