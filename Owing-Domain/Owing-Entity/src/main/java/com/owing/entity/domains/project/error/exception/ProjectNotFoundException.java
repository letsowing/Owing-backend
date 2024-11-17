package com.owing.entity.domains.project.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.project.error.ProjectErrorCode;

public class ProjectNotFoundException extends OwingException {

    private ProjectNotFoundException(ProjectErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static ProjectNotFoundException of(ProjectErrorCode errorCode) {
        return new ProjectNotFoundException(errorCode, null);
    }

    public static ProjectNotFoundException of(ProjectErrorCode errorCode, String description) {
        return new ProjectNotFoundException(errorCode, description);
    }
}
