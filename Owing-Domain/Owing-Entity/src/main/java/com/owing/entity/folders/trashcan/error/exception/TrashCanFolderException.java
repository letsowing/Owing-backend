package com.owing.entity.folders.trashcan.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.universe.error.UniverseErrorCode;
import com.owing.entity.folders.trashcan.error.TrashCanFolderErrorCode;

public class TrashCanFolderException extends OwingException {

    private TrashCanFolderException(TrashCanFolderErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static TrashCanFolderException of(TrashCanFolderErrorCode errorCode) {
        return new TrashCanFolderException(errorCode, null);
    }

    public static TrashCanFolderException of(TrashCanFolderErrorCode errorCode, String description) {
        return new TrashCanFolderException(errorCode, description);
    }
}
