package com.owing.api.story.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderDomainService;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteStoryFolderUseCase extends DeleteFolderUseCase<StoryFolder> {

    private final MemberUtils memberUtils;
    private final StoryFolderDomainService baseDndDomainService;
    private final TrashCanFolderDomainService trashCanFolderDomainService;
    private final ProjectAdapter projectAdapter;
    private final TrashCanMapper trashCanMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected BaseDndDomainService<StoryFolder> baseDndDomainService() {
        return baseDndDomainService;
    }

    @Override
    protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

    @Override
    protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

    @Override
    protected TrashCanMapper trashCanMapper() { return this.trashCanMapper; }
}
