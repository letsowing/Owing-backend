package com.owing.api.story.service.story;

import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.mapper.CrashCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.adapter.CrashCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.service.StoryDomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReadStoryCrashLogUseCase {

    private final StoryDomainService storyDomainService;
    private final CrashCheckLogAdapter crashCheckLogAdapter;
    private final CrashCheckLogMapper crashCheckLogMapper;

    public List<CrashCheckLogResponse> execute(Long storyId) {
        Story story = storyDomainService.getEntity(storyId);
        List<CrashCheckLog> crashCheckLogList = crashCheckLogAdapter.findByStoryId(story);

        return crashCheckLogMapper.toLogListResponse(crashCheckLogList);
    }
}
