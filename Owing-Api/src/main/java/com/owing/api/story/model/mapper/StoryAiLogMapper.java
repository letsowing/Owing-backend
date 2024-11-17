package com.owing.api.story.model.mapper;

import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.ai.log.story.model.SpellCheckOutput;
import com.owing.entity.domains.ai.log.story.model.StoryAiLog;
import com.owing.entity.domains.story.model.Story;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Mapper
@RequiredArgsConstructor
@Slf4j
public class StoryAiLogMapper {

    public SpellCheckOutput toSpellCheckOutput(StorySpellCheckResponse storySpellCheckResponse) {
        return SpellCheckOutput.builder()
                .help(storySpellCheckResponse.help())
                .errorIdx(storySpellCheckResponse.errorIdx())
                .correctMethod(storySpellCheckResponse.correctMethod())
                .start(storySpellCheckResponse.start())
                .errMsg(storySpellCheckResponse.errMsg())
                .end(storySpellCheckResponse.end())
                .orgStr(storySpellCheckResponse.orgStr())
                .candWord(storySpellCheckResponse.candWord())
                .build();
    }
    public StoryAiLog toEntity(Story story, List<StorySpellCheckResponse> aiResponse) {
        List<SpellCheckOutput> spellCheckOutputList = aiResponse.stream()
                .map(this::toSpellCheckOutput)
                .toList();
        return new StoryAiLog(story, spellCheckOutputList);
    }
}
