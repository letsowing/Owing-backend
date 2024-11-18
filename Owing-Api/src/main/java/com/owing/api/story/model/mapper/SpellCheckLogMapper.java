package com.owing.api.story.model.mapper;

import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.ai.log.story.model.SpellCheckOutput;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.story.model.Story;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Mapper
@RequiredArgsConstructor
@Slf4j
public class SpellCheckLogMapper {

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
    public SpellCheckLog toEntity(Story story, List<StorySpellCheckResponse> aiResponse) {
        List<SpellCheckOutput> spellCheckOutputList = aiResponse.stream()
                .map(this::toSpellCheckOutput)
                .toList();
        return new SpellCheckLog(story, spellCheckOutputList);
    }

    public StorySpellCheckResponse toStorySpellCheckResponse(SpellCheckOutput spellCheckOutput) {
        return StorySpellCheckResponse.builder()
                .help(spellCheckOutput.help())
                .errorIdx(spellCheckOutput.errorIdx())
                .correctMethod(spellCheckOutput.correctMethod())
                .start(spellCheckOutput.start())
                .errMsg(spellCheckOutput.errMsg())
                .end(spellCheckOutput.end())
                .orgStr(spellCheckOutput.orgStr())
                .candWord(spellCheckOutput.candWord())
                .build();
    }

    public StorySpellCheckLogResponse toLogResponse(SpellCheckLog spellCheckLog) {
        List<StorySpellCheckResponse> list = spellCheckLog.getOutput().stream().map(this::toStorySpellCheckResponse).toList();
        return new StorySpellCheckLogResponse(spellCheckLog.getId(), list, spellCheckLog.getCreatedAt());
    }

    public List<StorySpellCheckLogResponse> toLogListResponse(List<SpellCheckLog> spellCheckLogList) {
        return spellCheckLogList.stream()
                .map(this::toLogResponse)
                .toList();
    }
}
