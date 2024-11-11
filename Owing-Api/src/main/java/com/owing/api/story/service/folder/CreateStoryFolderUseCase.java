package com.owing.api.story.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.api.story.mapper.StoryFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateStoryFolderUseCase extends CreateFolderUseCase<StoryFolder> {

    private final MemberUtils memberUtils;
    private final StoryFolderDomainService baseDndDomainService;
    private final StoryFolderMapper dndMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected BaseDndDomainService<StoryFolder> baseDndDomainService() {
        return baseDndDomainService;
    }

    @Override
    protected BaseFolderMapper<StoryFolder> dndMapper() {
        return dndMapper;
    }
}
