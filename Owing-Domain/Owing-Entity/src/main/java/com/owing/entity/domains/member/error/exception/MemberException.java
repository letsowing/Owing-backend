package com.owing.entity.domains.member.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.member.error.MemberErrorCode;

public class MemberException extends OwingException {

    private MemberException(MemberErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static MemberException of(MemberErrorCode errorCode) {
        return new MemberException(errorCode, null);
    }

    public static MemberException of(MemberErrorCode errorCode, String description) {
        return new MemberException(errorCode, description);
    }
}
