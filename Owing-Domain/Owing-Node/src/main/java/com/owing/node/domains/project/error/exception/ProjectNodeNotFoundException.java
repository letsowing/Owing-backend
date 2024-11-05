package com.owing.node.domains.project.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.project.error.code.ProjectNodeErrorCode;

public class ProjectNodeNotFoundException extends OwingException {

    private ProjectNodeNotFoundException(ProjectNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static ProjectNodeNotFoundException of(ProjectNodeErrorCode errorCode) {
        return new ProjectNodeNotFoundException(errorCode, null);
    }

    public static ProjectNodeNotFoundException of(ProjectNodeErrorCode errorCode, String description) {
        return new ProjectNodeNotFoundException(errorCode, description);
    }
}