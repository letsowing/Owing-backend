package com.owing.entity.domains.member.error;

import com.owing.common.constant.OwingHttpStatus;
import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements OwingErrorCode {

    MEMBER_NOT_FOUND(OwingHttpStatus.NOT_FOUND, "001", "회원을 찾을 수 없습니다.");

    private final OwingHttpStatus status;
    private final String code;
    private final String message;

    MemberErrorCode(OwingHttpStatus status, String code, String message) {
        this.status = status;
        this.code = "MEMBER" + code;
        this.message = message;
    }
}