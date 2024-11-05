package com.owing.node.folder.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;

public class StoryFolderNodeRelationshipException extends OwingException {

    private StoryFolderNodeRelationshipException(StoryFolderNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryFolderNodeRelationshipException of(StoryFolderNodeErrorCode errorCode) {
        return new StoryFolderNodeRelationshipException(errorCode, null);
    }

    public static StoryFolderNodeRelationshipException of(StoryFolderNodeErrorCode errorCode, String description) {
        return new StoryFolderNodeRelationshipException(errorCode, description);
    }
}