package com.owing.api.story.service.story;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateStoryUseCase extends UpdateFileUseCase<Story, StoryFolder> {

    private final MemberUtils memberUtils;
    private final StoryService storyService;
    private final StoryMapper storyMapper;
    private final StoryAdapter storyAdapter;
    private final StoryFolderAdapter storyFolderAdapter;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndService<Story> fileService() {
        return storyService;
    }

    @Override
    protected BaseFileMapper<Story, StoryFolder> fileMapper() {
        return storyMapper;
    }

    @Override
    protected DndAdapter<Story> fileAdapter() {
        return storyAdapter;
    }

    @Override
    protected DndAdapter<StoryFolder> folderAdapter() {
        return storyFolderAdapter;
    }
}
