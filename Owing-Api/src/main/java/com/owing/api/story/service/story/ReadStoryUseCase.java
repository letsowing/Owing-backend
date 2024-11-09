package com.owing.api.story.service.story;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class ReadStoryUseCase extends ReadFileUseCase<Story, StoryFolder> {
    public ReadStoryUseCase(BaseFileMapper<Story, StoryFolder> dndMapper, BaseDndDomainService<Story> baseDndDomainService) {
        super(dndMapper, baseDndDomainService);
    }
}
