package com.owing.api.auth.error;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum AuthErrorCode implements OwingErrorCode {

    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "001", "권한이 없습니다."),
    FORBIDDEN_USER(FORBIDDEN, "002", "권한이 없는 유저 입니다"),
    INVALID_TOKEN(UNAUTHORIZED, "003", "유효하지 않은 토큰 입니다"),
    LOGIN_FAILED(UNAUTHORIZED, "004", "로그인에 실패했습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "005", "권한 정보가 없는 토큰 입니다"),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "006", "Refresh Token이 유효하지 않습니다"),
    REFRESH_TOKEN_NOT_FOUND(BAD_REQUEST, "007", "로그아웃 된 사용자입니다"),
    GOOGLE_SERVER_ERROR(BAD_REQUEST, "008", "Google Server 에러입니다."),
    INVALID_ID_TOKEN(UNAUTHORIZED, "009", "ID 토큰 검증에 실패했습니다."),
    INVALID_ACCESS_TOKEN(UNAUTHORIZED, "010", "Access Token이 유효하지 않습니다."),
    EXPIRE_ACCESS_TOKEN(UNAUTHORIZED, "011", "Access Token의 유효기간이 만료되었습니다."),
    NOT_TOKEN_USERID(UNAUTHORIZED, "012", "토큰에 유저아이디가 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    AuthErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "AUTH" + code;
        this.message = message;
    }
}
