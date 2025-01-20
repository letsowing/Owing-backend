package com.owing.api.story.service.folder;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderService;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteStoryFolderUseCase extends DeleteFolderUseCase<StoryFolder> {

    private final MemberUtils memberUtils;
    private final StoryFolderService storyFolderService;
    private final TrashCanFolderDomainService trashCanFolderDomainService;
    private final ProjectAdapter projectAdapter;
    private final TrashCanFolderMapper trashCanFolderMapper;
    private final StoryFolderAdapter storyFolderAdapter;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndService<StoryFolder> folderService() {
        return storyFolderService;
    }

    @Override
    protected DndAdapter<StoryFolder> folderAdapter() {
        return storyFolderAdapter;
    }

    @Override
    protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

    @Override
    protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

    @Override
    protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }
}
