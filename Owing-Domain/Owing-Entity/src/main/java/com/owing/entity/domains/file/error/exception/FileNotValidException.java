package com.owing.entity.domains.file.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.file.error.FileErrorCode;

public class FileNotValidException extends OwingException {

    private FileNotValidException(FileErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static FileNotValidException of(FileErrorCode errorCode) {
        return new FileNotValidException(errorCode, null);
    }

    public static FileNotValidException of(FileErrorCode errorCode, String description) {
        return new FileNotValidException(errorCode, description);
    }
}
