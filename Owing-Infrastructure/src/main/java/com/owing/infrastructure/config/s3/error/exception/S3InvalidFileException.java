package com.owing.infrastructure.config.s3.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.infrastructure.config.s3.error.S3ErrorCode;

public class S3InvalidFileException extends OwingException {

    private S3InvalidFileException(S3ErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static S3InvalidFileException of(S3ErrorCode errorCode) {
        return new S3InvalidFileException(errorCode, null);
    }

    public static S3InvalidFileException of(S3ErrorCode errorCode, String description) {
        return new S3InvalidFileException(errorCode, description);
    }
}
