package com.owing.entity.domains.trashcan.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.trashcan.error.TrashCanErrorCode;
import com.owing.entity.domains.universe.error.UniverseErrorCode;

public class TrashCanException extends OwingException {

    private TrashCanException(TrashCanErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static TrashCanException of(TrashCanErrorCode errorCode) {
        return new TrashCanException(errorCode, null);
    }

    public static TrashCanException of(TrashCanErrorCode errorCode, String description) {
        return new TrashCanException(errorCode, description);
    }
}
