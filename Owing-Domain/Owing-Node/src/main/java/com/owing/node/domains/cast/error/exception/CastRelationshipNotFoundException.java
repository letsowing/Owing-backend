package com.owing.node.domains.cast.error.exception;


import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;

public class CastRelationshipNotFoundException extends OwingException {
	private CastRelationshipNotFoundException(CastNodeErrorCode errorCode, String description) {
		super(errorCode, description);
	}

	public static CastRelationshipNotFoundException of(CastNodeErrorCode errorCode) {
		return new CastRelationshipNotFoundException(errorCode, null);
	}

	public static CastRelationshipNotFoundException of(CastNodeErrorCode errorCode, String description) {
		return new CastRelationshipNotFoundException(errorCode, description);
	}
}
