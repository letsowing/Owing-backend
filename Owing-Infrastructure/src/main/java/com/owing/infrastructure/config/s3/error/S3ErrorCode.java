package com.owing.infrastructure.config.s3.error;

import org.springframework.http.HttpStatus;

import com.owing.common.error.code.OwingErrorCode;

import lombok.Getter;

@Getter
public enum S3ErrorCode implements OwingErrorCode {

    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "001", "올바르지 않은 이미지 확장자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    S3ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "FILE" + code;
        this.message = message;
    }
}