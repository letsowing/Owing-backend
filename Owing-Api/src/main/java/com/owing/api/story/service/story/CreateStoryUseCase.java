package com.owing.api.story.service.story;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.api.story.model.dto.request.AddStoryContentRequest;
import com.owing.api.story.model.mapper.StoryContentMapper;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.statistics.service.DashboardService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryContent;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateStoryUseCase extends CreateFileUseCase<Story, StoryFolder> {
    private final MemberUtils memberUtils;
    private final StoryService storyService;
    private final StoryMapper storyMapper;
    private final StoryContentMapper storyContentMapper;
    private final StoryFolderAdapter folderAdapter;
    private final StoryAdapter storyAdapter;
    private final DashboardService dashboardService;

    public void executeText(Long storyId, AddStoryContentRequest request) {
        Long memberId = memberUtils.getCurrentMemberId();
        Story story = storyAdapter.findById(storyId);
        StoryContent storyContent = storyContentMapper.toEntity(request.content(), story);
        int beforeLength = story.getTextCount();

        storyService.updateContent(story, storyContent); // 내용 작성

        int afterLength = story.getTextCount();

        dashboardService.updateTodayCount(memberId, afterLength - beforeLength);
    }

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
    protected DndAdapter<StoryFolder> folderAdapter() {
        return folderAdapter;
    }

}
