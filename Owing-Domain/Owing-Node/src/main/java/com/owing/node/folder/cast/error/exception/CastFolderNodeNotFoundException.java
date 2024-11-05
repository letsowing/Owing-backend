package com.owing.node.folder.cast.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;

public class CastFolderNodeNotFoundException extends OwingException {

    private CastFolderNodeNotFoundException(CastFolderNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static CastFolderNodeNotFoundException of(CastFolderNodeErrorCode errorCode) {
        return new CastFolderNodeNotFoundException(errorCode, null);
    }

    public static CastFolderNodeNotFoundException of(CastFolderNodeErrorCode errorCode, String description) {
        return new CastFolderNodeNotFoundException(errorCode, description);
    }
}