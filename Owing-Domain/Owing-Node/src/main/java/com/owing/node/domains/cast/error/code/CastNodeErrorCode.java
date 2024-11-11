package com.owing.node.domains.cast.error.code;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CastNodeErrorCode implements OwingErrorCode {
	CAST_NODE_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "인물을 찾을 수 없습니다."),
	INVALID_ARGS_FOR_UPDATE(HttpStatus.BAD_REQUEST, "002", "업데이트 요청에 잘못된 데이터가 포함되었습니다."),
	CONNECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "003", "관계를 찾을 수 없습니다."),
	ILLEGAL_ARGS(HttpStatus.BAD_REQUEST, "004", "올바르지 않은 인자값입니다."),
	CONNECTION_NAME_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "005", "관계 이름 업데이트를 실패했습니다."),
	INVALID_DELETE_COUNT(HttpStatus.CONFLICT, "006", "올바르지 않은 관계 정보로 인해 삭제에 실패했습니다."),
	ILLEGAL_HANDLE_ARGS(HttpStatus.BAD_REQUEST, "007", "올바르지 않은 관계 Handle입니다."),
	ILLEGAL_TYPE_ARGS(HttpStatus.BAD_REQUEST, "008", "올바르지 않은 관계 Type입니다."),
	INVALID_POSITION(HttpStatus.BAD_REQUEST, "009", "인물의 위치를 변경할 수 없습니다."),
	RELATED_FOLDER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "010", "이미 소속된 폴더가 존재합니다."),
	CONNECTION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "010", "이미 연결된 관계가 존재합니다."),
	RELATIONSHIP_NOT_FOUND(HttpStatus.BAD_REQUEST, "011", "관계를 찾을 수 없습니다."),
	;


	private final HttpStatus status;
	private final String code;
	private final String message;

	CastNodeErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = "CAST_NODE" + code;
		this.message = message;
	}
}
