package com.owing.core.dnd.base.error;

import org.springframework.http.HttpStatus;

import com.owing.common.error.code.OwingErrorCode;

import lombok.Getter;

@Getter
public enum DndErrorCode implements OwingErrorCode {

	DND_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "아이템을 찾을 수 없습니다."),
	INVALID_POSITION(HttpStatus.BAD_REQUEST, "002", "올바르지 않은 위치입니다."),
	INVALID_TITLE(HttpStatus.BAD_REQUEST, "003", "제목을 입력해 주세요.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	DndErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = "DND" + code;
		this.message = message;
	}
}