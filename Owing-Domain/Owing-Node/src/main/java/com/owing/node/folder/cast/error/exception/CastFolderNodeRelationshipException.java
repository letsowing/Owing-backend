package com.owing.node.folder.cast.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;

public class CastFolderNodeRelationshipException extends OwingException {

    private CastFolderNodeRelationshipException(CastFolderNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static CastFolderNodeRelationshipException of(CastFolderNodeErrorCode errorCode) {
        return new CastFolderNodeRelationshipException(errorCode, null);
    }

    public static CastFolderNodeRelationshipException of(CastFolderNodeErrorCode errorCode, String description) {
        return new CastFolderNodeRelationshipException(errorCode, description);
    }
}