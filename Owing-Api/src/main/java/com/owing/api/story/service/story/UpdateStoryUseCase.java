package com.owing.api.story.service.story;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class UpdateStoryUseCase extends UpdateFileUseCase<Story, StoryFolder> {
    public UpdateStoryUseCase(MemberUtils memberUtils,
        BaseDndDomainService<Story> baseDndDomainService,
        BaseDndDomainService<StoryFolder> folderBaseDndDomainService,
        BaseFileMapper<Story, StoryFolder> dndMapper) {
        super(memberUtils, baseDndDomainService, folderBaseDndDomainService, dndMapper);
    }

}
