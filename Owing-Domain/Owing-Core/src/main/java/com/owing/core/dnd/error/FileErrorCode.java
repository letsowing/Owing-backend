package com.owing.core.dnd.error;

import org.springframework.http.HttpStatus;

import com.owing.common.error.code.OwingErrorCode;

import lombok.Getter;

@Getter
public enum FileErrorCode implements OwingErrorCode {

	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "파일을 찾을 수 없습니다."),
	INVALID_POSITION(HttpStatus.BAD_REQUEST, "002", "올바르지 않은 폴더 위치입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	FileErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = "FOLDER" + code;
		this.message = message;
	}
}