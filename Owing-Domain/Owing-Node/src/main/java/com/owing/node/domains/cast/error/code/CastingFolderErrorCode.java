package com.owing.node.domains.cast.error.code;

import com.owing.common.constant.OwingHttpStatus;
import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;

@Getter
public enum CastingFolderErrorCode implements OwingErrorCode {
	FOLDER_NOT_FOUND(OwingHttpStatus.NOT_FOUND, "001", "폴더를 찾을 수 없습니다."),
	INVALID_POSITION(OwingHttpStatus.BAD_REQUEST, "002", "폴더 위치가 올바르지 않습니다."),
	;

	private final OwingHttpStatus status;
	private final String code;
	private final String message;

	CastingFolderErrorCode(OwingHttpStatus status, String code, String message) {
		this.status = status;
		this.code = "Folder" + code;
		this.message = message;
	}
}
