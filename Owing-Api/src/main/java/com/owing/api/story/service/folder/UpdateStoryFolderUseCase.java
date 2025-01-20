package com.owing.api.story.service.folder;

import com.owing.api.story.model.mapper.StoryFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateStoryFolderUseCase extends UpdateFolderUseCase<StoryFolder> {
    private final MemberUtils memberUtils;
    private final StoryFolderService storyFolderService;
    private final StoryFolderMapper storyFolderMapper;
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
    protected BaseFolderMapper<StoryFolder> folderMapper() {
        return storyFolderMapper;
    }

    @Override
    protected DndAdapter<StoryFolder> folderAdapter() {
        return storyFolderAdapter;
    }
}
