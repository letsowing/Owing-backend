package com.owing.node.folder.story.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;

public class StoryFolderNodeNotFoundException extends OwingException {

    private StoryFolderNodeNotFoundException(StoryFolderNodeErrorCode errorCode, String description) {
        super(errorCode, description);
    }

    public static StoryFolderNodeNotFoundException of(StoryFolderNodeErrorCode errorCode) {
        return new StoryFolderNodeNotFoundException(errorCode, null);
    }

    public static StoryFolderNodeNotFoundException of(StoryFolderNodeErrorCode errorCode, String description) {
        return new StoryFolderNodeNotFoundException(errorCode, description);
    }
}