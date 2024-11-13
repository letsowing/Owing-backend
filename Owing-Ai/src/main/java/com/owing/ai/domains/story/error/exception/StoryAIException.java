package com.owing.ai.domains.story.error.exception;

import com.owing.ai.domains.story.error.StoryAIErrorCode;
import com.owing.common.error.exception.OwingException;

public class StoryAIException extends OwingException {

    private StoryAIException(StoryAIErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryAIException of(StoryAIErrorCode errorCode) {
        return new StoryAIException(errorCode, null);
    }

    public static StoryAIException of(StoryAIErrorCode errorCode, String description) {
        return new StoryAIException(errorCode, description);
    }
}
