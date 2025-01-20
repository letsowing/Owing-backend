package com.owing.api.story.service.story;

import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.service.StoryService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteStoryUseCase extends DeleteFileUseCase<Story> {

    private final MemberUtils memberUtils;
    private final StoryService storyService;
    private final TrashCanDomainService trashCanDomainService;
    private final TrashCanMapper trashCanMapper;
    private final StoryAdapter storyAdapter;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndAdapter<Story> fileAdapter() {
        return storyAdapter;
    }

    @Override
    protected DndService<Story> fileService() {
        return storyService;
    }

    @Override
    protected TrashCanDomainService trashCanDomainService() {
        return trashCanDomainService;
    }

    @Override
    protected TrashCanMapper trashCanMapper() {
        return trashCanMapper;
    }
}
