package com.owing.node.domains.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;

public class StoryNodeNotFoundException extends OwingException {

    private StoryNodeNotFoundException(StoryNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryNodeNotFoundException of(StoryNodeErrorCode errorCode) {
        return new StoryNodeNotFoundException(errorCode, null);
    }

    public static StoryNodeNotFoundException of(StoryNodeErrorCode errorCode, String description) {
        return new StoryNodeNotFoundException(errorCode, description);
    }
}
