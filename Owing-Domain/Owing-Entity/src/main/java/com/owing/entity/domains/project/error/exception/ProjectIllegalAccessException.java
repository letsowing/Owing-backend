package com.owing.entity.domains.project.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.project.error.ProjectErrorCode;

public class ProjectIllegalAccessException extends OwingException {

    private ProjectIllegalAccessException(ProjectErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static ProjectIllegalAccessException of(ProjectErrorCode errorCode) {
        return new ProjectIllegalAccessException(errorCode, null);
    }

    public static ProjectIllegalAccessException of(ProjectErrorCode errorCode, String description) {
        return new ProjectIllegalAccessException(errorCode, description);
    }
}
