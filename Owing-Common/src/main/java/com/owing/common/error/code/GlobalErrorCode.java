package com.owing.common.error.code;

import com.owing.common.constant.OwingHttpStatus;
import lombok.Getter;

@Getter
public enum GlobalErrorCode implements OwingErrorCode{
	ERROR(OwingHttpStatus.BAD_REQUEST, "001", "알수없는 에러입니다. 담당자에게 문의해주세요."),
	RUNTIME_ERROR(OwingHttpStatus.BAD_REQUEST, "002", "알수없는 에러입니다. 담당자에게 문의해주세요."),
	ILLEGAL_PATH(OwingHttpStatus.BAD_REQUEST, "003", "잘못된 주소입니다."),
	ILLEGAL_PATH_ARGS(OwingHttpStatus.BAD_REQUEST, "004", "잘못된 주소 ARGS 입력값입니다."),
	JSON_PARSING_ERROR(OwingHttpStatus.BAD_REQUEST, "005", "JSON 파싱 오류"),
	JSON_SERIALIZATION_ERROR(OwingHttpStatus.BAD_REQUEST, "006", "JSON 직렬화 오류");

	private final OwingHttpStatus status;
	private final String code;
	private final String message;

	GlobalErrorCode(OwingHttpStatus status, String code, String message) {
		this.status = status;
		this.code = "GLOBAL"+code;
		this.message = message;
	}
}
