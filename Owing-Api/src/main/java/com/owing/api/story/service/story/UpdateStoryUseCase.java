package com.owing.api.story.service.story;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryDomainService;
import com.owing.entity.domains.story.service.StoryFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateStoryUseCase extends UpdateFileUseCase<Story, StoryFolder> {

    private final MemberUtils memberUtils;
    private final StoryDomainService baseDndDomainService;
    private final StoryFolderDomainService folderBaseDndDomainService;
    private final StoryMapper dndMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected BaseDndDomainService<Story> baseDndDomainService() {
        return baseDndDomainService;
    }

    @Override
    protected BaseDndDomainService<StoryFolder> fBaseDndDomainService() {
        return folderBaseDndDomainService;
    }

    @Override
    protected BaseFileMapper<Story, StoryFolder> dndMapper() {
        return dndMapper;
    }
}
