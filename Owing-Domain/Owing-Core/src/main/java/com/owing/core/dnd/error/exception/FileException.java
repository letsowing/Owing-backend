package com.owing.core.dnd.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.core.dnd.error.FileErrorCode;

public class FileException extends OwingException {

    private FileException(FileErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FileException of(FileErrorCode errorCode) {
        return new FileException(errorCode, null);
    }

    public static FileException of(FileErrorCode errorCode, String description) {
        return new FileException(errorCode, description);
    }
}
