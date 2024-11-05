package com.owing.node.domains.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;

public class StoryNodeException extends OwingException {

    private StoryNodeException(StoryNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryNodeException of(StoryNodeErrorCode errorCode) {
        return new StoryNodeException(errorCode, null);
    }

    public static StoryNodeException of(StoryNodeErrorCode errorCode, String description) {
        return new StoryNodeException(errorCode, description);
    }
}