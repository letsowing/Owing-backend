package com.owing.entity.domains.universe.error;

import com.owing.common.constant.OwingHttpStatus;
import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;

@Getter
public enum UniverseErrorCode implements OwingErrorCode {

    CAST_NOT_FOUND(OwingHttpStatus.NOT_FOUND, "001", "등장인물을 찾을 수 없습니다.");

    private final OwingHttpStatus status;
    private final String code;
    private final String message;

    UniverseErrorCode(OwingHttpStatus status, String code, String message) {
        this.status = status;
        this.code = "MEMBER" + code;
        this.message = message;
    }
}