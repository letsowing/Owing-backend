package com.owing.entity.domains.member.error;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RefreshTokenErrorCode implements OwingErrorCode {

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "회원을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    RefreshTokenErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "REFRESH_TOKEN" + code;
        this.message = message;
    }
}
