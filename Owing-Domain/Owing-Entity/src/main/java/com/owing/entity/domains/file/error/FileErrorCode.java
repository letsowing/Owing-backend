package com.owing.entity.domains.file.error;

import org.springframework.http.HttpStatus;

import com.owing.common.error.code.OwingErrorCode;

import lombok.Getter;

@Getter
public enum FileErrorCode implements OwingErrorCode {

    FILE_NOT_VALID(HttpStatus.BAD_REQUEST, "001", "올바르지 않은 이미지 확장자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    FileErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "FILE" + code;
        this.message = message;
    }
}