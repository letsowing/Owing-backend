package com.owing.node.domains.story.adapter;


import com.owing.common.annotation.Adaptor;
import com.owing.node.domains.story.error.code.StoryNodeErrorCode;
import com.owing.node.domains.story.error.exception.StoryNodeNotFoundException;
import com.owing.node.domains.story.model.StoryNode;
import com.owing.node.domains.story.repository.StoryNodeRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryNodeAdapter {

    private final StoryNodeRepository storyNodeRepository;

    public StoryNode findById(Long storyId) {
        return storyNodeRepository.findById(storyId)
                .orElseThrow(() -> StoryNodeNotFoundException.of(StoryNodeErrorCode.STORY_NODE_NOT_FOUND, "Story Node Id: %d".formatted(storyId)));
    }
}
