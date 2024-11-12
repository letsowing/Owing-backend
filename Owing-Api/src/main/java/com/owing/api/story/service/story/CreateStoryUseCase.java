package com.owing.api.story.service.story;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.api.story.model.dto.request.AddStoryContentRequest;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.api.story.model.mapper.StoryContentMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.model.StoryContent;
import com.owing.entity.domains.story.service.StoryDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateStoryUseCase extends CreateFileUseCase<Story, StoryFolder> {
    private final MemberUtils memberUtils;
    private final StoryDomainService storyDomainService;
    private final StoryMapper storyMapper;
    private final StoryContentMapper storyContentMapper;
    private final StoryFolderAdapter folderAdapter;

    public void executeText(Long storyId, AddStoryContentRequest request) {
        Story story = storyDomainService.getEntity(storyId);
        StoryContent storyContent = storyContentMapper.toEntity(request.content(), story);
        storyDomainService.updateContent(story, storyContent);
        story.createOrUpdateStoryText(storyContent);
    }

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected BaseDndDomainService<Story> baseDndDomainService() {
        return storyDomainService;
    }

    @Override
    protected BaseFileMapper<Story, StoryFolder> dndMapper() {
        return storyMapper;
    }

    @Override
    protected BaseDndAdapter<StoryFolder> folderAdapter() {
        return folderAdapter;
    }

}
