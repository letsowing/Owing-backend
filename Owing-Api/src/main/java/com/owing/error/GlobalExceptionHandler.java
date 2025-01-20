package com.owing.error;

import com.owing.common.error.code.GlobalErrorCode;
import com.owing.common.error.dto.ErrorResponse;
import com.owing.common.error.exception.OwingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleOwingException (OwingException e) {
		log.error("class: {}, message: {}, desc: {}", e.getClass(), e.getMessage(), e.getDescription());
		log.error(Arrays.toString(e.getStackTrace()));
		return createErrorResponse(e.getErrorCode(), e.getDescription());
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		return createErrorResponse(
				GlobalErrorCode.ILLEGAL_ARGUMENT,
				e.getBindingResult().getAllErrors().getFirst().getDefaultMessage()
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException (Exception e) {
		log.error("class: {}, message: {}", e.getClass(), e.getMessage());
		log.error(Arrays.toString(e.getStackTrace()));
		e.printStackTrace();
		return createErrorResponse(GlobalErrorCode.ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException (RuntimeException e) {
		log.error("class: {}, message: {}", e.getClass(), e.getMessage());
		log.error(Arrays.toString(e.getStackTrace()));
		e.printStackTrace();
		return createErrorResponse(GlobalErrorCode.RUNTIME_ERROR);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMissingPathVariableException (MethodArgumentTypeMismatchException e) {
		log.error("class: {}, message: {}", e.getClass(), e.getMessage());
		log.error(Arrays.toString(e.getStackTrace()));
		return createErrorResponse(GlobalErrorCode.ILLEGAL_PATH_ARGS);
	}
}
