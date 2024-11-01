package com.owing.node.domains.cast.error.exception;


import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.cast.error.code.CastingNodeErrorCode;

public class CastingException extends OwingException {
	private CastingException(CastingNodeErrorCode errorCode, String description) {
		super(errorCode, description);
	}

	public static CastingException of(CastingNodeErrorCode errorCode) {
		return new CastingException(errorCode, null);
	}

	public static CastingException of(CastingNodeErrorCode errorCode, String description) {
		return new CastingException(errorCode, description);
	}
}
