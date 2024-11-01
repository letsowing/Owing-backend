package com.owing.entity.domains.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.story.error.StoryErrorCode;

public class StoryException extends OwingException {

    private StoryException(StoryErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryException of(StoryErrorCode errorCode) {
        return new StoryException(errorCode, null);
    }

    public static StoryException of(StoryErrorCode errorCode, String description) {
        return new StoryException(errorCode, description);
    }
}
