package com.owing.api.story.service;

import java.util.List;

import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.model.mapper.SpellCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.adapter.SpellCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadStorySpellLogUseCase {

    private final StoryAdapter storyAdapter;
    private final SpellCheckLogAdapter spellCheckLogAdapter;
    private final SpellCheckLogMapper spellCheckLogMapper;

    public List<StorySpellCheckLogResponse> execute(Long storyId) {
        Story story = storyAdapter.findById(storyId);
        List<SpellCheckLog> spellCheckLogList = spellCheckLogAdapter.findByStoryId(story);

        return spellCheckLogMapper.toLogListResponse(spellCheckLogList);
    }
}
