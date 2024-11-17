package com.owing.node.folder.cast.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;

public class CastFolderNodeException extends OwingException {

    private CastFolderNodeException(CastFolderNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static CastFolderNodeException of(CastFolderNodeErrorCode errorCode) {
        return new CastFolderNodeException(errorCode, null);
    }

    public static CastFolderNodeException of(CastFolderNodeErrorCode errorCode, String description) {
        return new CastFolderNodeException(errorCode, description);
    }
}
