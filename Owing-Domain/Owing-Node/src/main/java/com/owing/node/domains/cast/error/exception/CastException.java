package com.owing.node.domains.cast.error.exception;


import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;

public class CastException extends OwingException {
	private CastException(CastNodeErrorCode errorCode, String description) {
		super(errorCode, description);
	}

	public static CastException of(CastNodeErrorCode errorCode) {
		return new CastException(errorCode, null);
	}

	public static CastException of(CastNodeErrorCode errorCode, String description) {
		return new CastException(errorCode, description);
	}
}
