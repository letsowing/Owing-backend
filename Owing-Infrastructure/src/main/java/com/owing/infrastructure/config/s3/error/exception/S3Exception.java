package com.owing.infrastructure.config.s3.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.infrastructure.config.s3.error.S3ErrorCode;

public class S3Exception extends OwingException {

    private S3Exception(S3ErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static S3Exception of(S3ErrorCode errorCode) {
        return new S3Exception(errorCode, null);
    }

    public static S3Exception of(S3ErrorCode errorCode, String description) {
        return new S3Exception(errorCode, description);
    }
}
