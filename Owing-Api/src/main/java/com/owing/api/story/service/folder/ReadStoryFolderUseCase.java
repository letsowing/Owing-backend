package com.owing.api.story.service.folder;

import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.api.story.model.mapper.StoryFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadStoryFolderUseCase extends ReadFolderUseCase<StoryFolder> {
    private final StoryFolderMapper dndMapper;
    private final StoryFolderAdapter storyFolderAdapter;

    @Override
    protected DndAdapter<StoryFolder> folderAdapter() {
        return storyFolderAdapter;
    }

    @Override
    protected BaseFolderMapper<StoryFolder> folderMapper() {
        return dndMapper;
    }
}
