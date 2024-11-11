package com.owing.node.folder.story.adaptor;

import com.owing.common.annotation.Adaptor;
import com.owing.node.folder.story.error.code.StoryFolderNodeErrorCode;
import com.owing.node.folder.story.error.exception.StoryFolderNodeNotFoundException;
import com.owing.node.folder.story.model.StoryFolderNode;
import com.owing.node.folder.story.repository.StoryFolderNodeRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryFolderNodeAdaptor {

    private final StoryFolderNodeRepository repository;

    public StoryFolderNode findById(Long storyFolderId) {
        return repository.findById(storyFolderId)
                .orElseThrow(() -> StoryFolderNodeNotFoundException.of(StoryFolderNodeErrorCode.STORY_FOLDER_NODE_ERROR_CODE, "story folder id: %d".formatted(storyFolderId)));
    }
}
