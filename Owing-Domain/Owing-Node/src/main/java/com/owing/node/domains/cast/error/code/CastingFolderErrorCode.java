package com.owing.node.domains.cast.error.code;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CastingFolderErrorCode implements OwingErrorCode {
	FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "폴더를 찾을 수 없습니다."),
	INVALID_POSITION(HttpStatus.BAD_REQUEST, "002", "폴더 위치가 올바르지 않습니다."),
	;

	private final HttpStatus status;
	private final String code;
	private final String message;

	CastingFolderErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = "Folder" + code;
		this.message = message;
	}
}
