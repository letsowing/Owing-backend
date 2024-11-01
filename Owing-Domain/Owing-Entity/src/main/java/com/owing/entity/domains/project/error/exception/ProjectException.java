package com.owing.entity.domains.project.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.project.error.ProjectErrorCode;

public class ProjectException extends OwingException {

    private ProjectException(ProjectErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static ProjectException of(ProjectErrorCode errorCode) {
        return new ProjectException(errorCode, null);
    }

    public static ProjectException of(ProjectErrorCode errorCode, String description) {
        return new ProjectException(errorCode, description);
    }
}
