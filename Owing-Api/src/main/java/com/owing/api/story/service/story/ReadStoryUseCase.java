package com.owing.api.story.service.story;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadStoryUseCase extends ReadFileUseCase<Story, StoryFolder> {
    private final StoryMapper storyMapper;
    private final StoryAdapter storyAdapter;

    @Override
    protected BaseFileMapper<Story, StoryFolder> fileMapper() {
        return storyMapper;
    }

    @Override
    protected DndAdapter<Story> fileAdapter() {
        return storyAdapter;
    }
}
