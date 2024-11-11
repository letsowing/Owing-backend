package com.owing.core.dnd.base.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.core.dnd.base.error.FileErrorCode;

public class FileNotFoundException extends OwingException {

    private FileNotFoundException(FileErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FileNotFoundException of(FileErrorCode errorCode) {
        return new FileNotFoundException(errorCode, null);
    }

    public static FileNotFoundException of(FileErrorCode errorCode, String description) {
        return new FileNotFoundException(errorCode, description);
    }
}
