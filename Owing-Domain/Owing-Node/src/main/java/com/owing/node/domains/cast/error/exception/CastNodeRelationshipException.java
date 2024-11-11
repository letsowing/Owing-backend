package com.owing.node.domains.cast.error.exception;


import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;

public class CastNodeRelationshipException extends OwingException {
	private CastNodeRelationshipException(CastNodeErrorCode errorCode, String description) {
		super(errorCode, description);
	}

	public static CastNodeRelationshipException of(CastNodeErrorCode errorCode) {
		return new CastNodeRelationshipException(errorCode, null);
	}

	public static CastNodeRelationshipException of(CastNodeErrorCode errorCode, String description) {
		return new CastNodeRelationshipException(errorCode, description);
	}
}
