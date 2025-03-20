package com.owing.api.story.service;

import java.util.List;

import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.mapper.CrashCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.adapter.CrashCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadStoryCrashLogUseCase {

    private final StoryAdapter storyAdapter;
    private final CrashCheckLogAdapter crashCheckLogAdapter;
    private final CrashCheckLogMapper crashCheckLogMapper;

    public List<CrashCheckLogResponse> execute(Long storyId) {
        Story story = storyAdapter.findById(storyId);
        List<CrashCheckLog> crashCheckLogList = crashCheckLogAdapter.findByStoryId(story);

        return crashCheckLogMapper.toLogListResponse(crashCheckLogList);
    }
}
