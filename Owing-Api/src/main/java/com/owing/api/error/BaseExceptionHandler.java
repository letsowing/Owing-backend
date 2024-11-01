package com.owing.api.error;


import com.owing.common.error.code.OwingErrorCode;
import com.owing.common.error.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;

public abstract class BaseExceptionHandler {
	protected ResponseEntity<ErrorResponse> createErrorResponse(OwingErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getStatus().value()).body(ErrorResponse.of(errorCode));
	}

	protected ResponseEntity<ErrorResponse> createErrorResponse(OwingErrorCode errorCode, String description) {
		return ResponseEntity.status(errorCode.getStatus().value()).body(ErrorResponse.of(errorCode, description));
	}
}
