package com.owing.api.story.service.story;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.api.story.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryDomainService;

@UseCase
public class CreateStoryUseCase extends CreateFileUseCase<Story, StoryFolder> {
    public CreateStoryUseCase(MemberUtils memberUtils,
        StoryDomainService dndDomainService,
        StoryMapper dndMapper,
        StoryFolderAdapter folderAdapter) {
        super(memberUtils, dndDomainService, dndMapper, folderAdapter);
    }

}
