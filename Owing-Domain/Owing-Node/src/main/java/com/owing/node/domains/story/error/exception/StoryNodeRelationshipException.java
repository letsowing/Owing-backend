package com.owing.node.domains.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;

public class StoryNodeRelationshipException extends OwingException {

    private StoryNodeRelationshipException(StoryNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryNodeRelationshipException of(StoryNodeErrorCode errorCode) {
        return new StoryNodeRelationshipException(errorCode, null);
    }

    public static StoryNodeRelationshipException of(StoryNodeErrorCode errorCode, String description) {
        return new StoryNodeRelationshipException(errorCode, description);
    }
}
