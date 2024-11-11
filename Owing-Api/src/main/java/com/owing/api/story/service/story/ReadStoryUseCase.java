package com.owing.api.story.service.story;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.api.story.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadStoryUseCase extends ReadFileUseCase<Story, StoryFolder> {
    private final StoryMapper dndMapper;
    private final StoryDomainService baseDndDomainService;

    @Override
    protected BaseFileMapper<Story, StoryFolder> dndMapper() {
        return dndMapper;
    }

    @Override
    protected BaseDndDomainService<Story> baseDndDomainService() {
        return baseDndDomainService;
    }
}
