package com.owing.node.domains.cast.error.exception;


import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;

public class CastNodeNotFoundException extends OwingException {
	private CastNodeNotFoundException(CastNodeErrorCode errorCode, String description) {
		super(errorCode, description);
	}

	public static CastNodeNotFoundException of(CastNodeErrorCode errorCode) {
		return new CastNodeNotFoundException(errorCode, null);
	}

	public static CastNodeNotFoundException of(CastNodeErrorCode errorCode, String description) {
		return new CastNodeNotFoundException(errorCode, description);
	}
}
