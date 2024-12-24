package com.owing.api.story.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateStoryFolderUseCase extends UpdateFolderUseCase<StoryFolder> {
    private final MemberUtils memberUtils;
    private final DndDomainService<StoryFolder> dndDomainService;
    private final BaseFolderMapper<StoryFolder> dndMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndDomainService<StoryFolder> baseDndDomainService() {
        return dndDomainService;
    }

    @Override
    protected BaseFolderMapper<StoryFolder> dndMapper() {
        return dndMapper;
    }
}
