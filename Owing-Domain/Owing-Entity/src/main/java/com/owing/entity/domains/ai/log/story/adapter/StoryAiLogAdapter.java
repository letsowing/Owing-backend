package com.owing.entity.domains.ai.log.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.ai.log.story.model.StoryAiLog;
import com.owing.entity.domains.ai.log.story.repository.StoryAiLogRepository;
import com.owing.entity.domains.story.model.Story;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class StoryAiLogAdapter {

    private final StoryAiLogRepository storyAiLogRepository;

    public List<StoryAiLog> findByStoryId(Story story) {
        return storyAiLogRepository.findByStory(story);
    }

}
