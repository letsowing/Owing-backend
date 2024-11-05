package com.owing.node.domains.project.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.project.error.code.ProjectNodeErrorCode;

public class ProjectNodeException extends OwingException {

    private ProjectNodeException(ProjectNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static ProjectNodeException of(ProjectNodeErrorCode errorCode) {
        return new ProjectNodeException(errorCode, null);
    }

    public static ProjectNodeException of(ProjectNodeErrorCode errorCode, String description) {
        return new ProjectNodeException(errorCode, description);
    }
}