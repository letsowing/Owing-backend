package com.owing.api.story.service;

import com.owing.api.story.model.dto.request.AddStoryContentRequest;
import com.owing.api.story.model.mapper.StoryContentMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.entity.domains.statistics.service.DashboardService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryContent;
import com.owing.entity.domains.story.service.StoryService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateStoryUseCase {
    private final MemberUtils memberUtils;
    private final StoryService storyService;
    private final StoryContentMapper storyContentMapper;
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

}
