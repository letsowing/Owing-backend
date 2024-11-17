package com.owing.entity.domains.ai.log.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.ai.log.story.repository.SpellCheckLogRepository;
import com.owing.entity.domains.story.model.Story;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class SpellCheckLogAdapter {

    private final SpellCheckLogRepository spellCheckLogRepository;

    public List<SpellCheckLog> findByStoryId(Story story) {
        return spellCheckLogRepository.findByStoryOrderByCreatedAtAsc(story);
    }

}
