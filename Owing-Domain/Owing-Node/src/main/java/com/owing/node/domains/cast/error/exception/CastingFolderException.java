package com.owing.node.domains.cast.error.exception;


import com.owing.common.error.code.OwingErrorCode;
import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.cast.error.code.CastingFolderErrorCode;

public class CastingFolderException extends OwingException {
	private CastingFolderException(OwingErrorCode errorCode, String description) {
		super(errorCode, description);
	}

	public static CastingFolderException of(CastingFolderErrorCode errorCode) {
		return new CastingFolderException(errorCode, null);
	}

	public static CastingFolderException of(CastingFolderErrorCode errorCode, String description) {
		return new CastingFolderException(errorCode, description);
	}
}
