package com.owing.api.story.service.story;

import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.model.mapper.SpellCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.adapter.SpellCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.service.StoryDomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReadStorySpellLogUseCase {

    private final StoryDomainService storyDomainService;
    private final SpellCheckLogAdapter spellCheckLogAdapter;
    private final SpellCheckLogMapper spellCheckLogMapper;

    public List<StorySpellCheckLogResponse> execute(Long storyId) {
        Story story = storyDomainService.getEntity(storyId);
        List<SpellCheckLog> spellCheckLogList = spellCheckLogAdapter.findByStoryId(story);

        return spellCheckLogMapper.toLogListResponse(spellCheckLogList);
    }
}
