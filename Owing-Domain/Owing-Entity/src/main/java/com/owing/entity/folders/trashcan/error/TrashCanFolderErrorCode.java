package com.owing.entity.folders.trashcan.error;

import org.springframework.http.HttpStatus;

import com.owing.common.error.code.OwingErrorCode;

import lombok.Getter;

@Getter
public enum TrashCanFolderErrorCode implements OwingErrorCode {

    TRASH_CAN_FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "해당 쓰레기통 폴더를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    TrashCanFolderErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = "TRASH_CAN_FOLDER" + code;
        this.message = message;
    }
}