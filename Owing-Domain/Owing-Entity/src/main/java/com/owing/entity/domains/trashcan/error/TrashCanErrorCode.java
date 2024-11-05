package com.owing.entity.domains.trashcan.error;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TrashCanErrorCode implements OwingErrorCode {

    TRASH_CAN_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "휴지통을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    TrashCanErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "TRASH_CAN" + code;
        this.message = message;
    }
}