package com.owing.api.story.service.story;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class ReadStoryUseCase extends ReadFileUseCase<Story, StoryFolder> {
    public ReadStoryUseCase(BaseFileMapper<Story, StoryFolder> dndMapper, DndDomainService<Story> dndDomainService) {
        super(dndMapper, dndDomainService);
    }
}
