package com.owing.node.folder.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;

public class StoryFolderNodeException extends OwingException {

    private StoryFolderNodeException(StoryFolderNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryFolderNodeException of(StoryFolderNodeErrorCode errorCode) {
        return new StoryFolderNodeException(errorCode, null);
    }

    public static StoryFolderNodeException of(StoryFolderNodeErrorCode errorCode, String description) {
        return new StoryFolderNodeException(errorCode, description);
    }
}
