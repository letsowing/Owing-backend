package com.owing.common.error.exception;

import com.owing.common.error.code.OwingErrorCode;
import lombok.Getter;

@Getter
public class OwingException extends RuntimeException{
	private final OwingErrorCode errorCode;
	private final String description;

	protected OwingException(OwingErrorCode errorCode, String description) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
        this.description = description;
    }

	protected static OwingException of(OwingErrorCode errorCode){
		return new OwingException(errorCode, null);
	}

	protected static OwingException of(OwingErrorCode errorCode, String description){
		return new OwingException(errorCode, description);
	}
}
