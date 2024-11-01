package com.owing.common.error.dto;

import com.owing.common.error.code.OwingErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
	private final String code;
	private final String message;
	private final String description;

	public static ErrorResponse of(OwingErrorCode errorCode) {
		return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
	}

	public static ErrorResponse of(OwingErrorCode errorCode, String description) {
		return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), description);
	}
}
