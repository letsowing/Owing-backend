package com.owing.api.story.service.folder;

import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.api.story.model.mapper.StoryFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadStoryFolderUseCase extends ReadFolderUseCase<StoryFolder> {
    private final StoryFolderMapper dndMapper;
    private final StoryFolderDomainService baseDndDomainService;

    @Override
    protected BaseFolderMapper<StoryFolder> dndMapper() {
        return dndMapper;
    }

    @Override
    protected BaseDndDomainService<StoryFolder> baseDndDomainService() {
        return baseDndDomainService;
    }
}
