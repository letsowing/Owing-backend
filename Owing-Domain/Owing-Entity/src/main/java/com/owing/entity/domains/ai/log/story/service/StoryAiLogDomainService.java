package com.owing.entity.domains.ai.log.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.ai.log.story.adapter.StoryAiLogAdapter;
import com.owing.entity.domains.ai.log.story.model.StoryAiLog;
import com.owing.entity.domains.ai.log.story.repository.StoryAiLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryAiLogDomainService {

    private final StoryAiLogAdapter storyAiLogAdapter;
    private final StoryAiLogRepository storyAiLogRepository;

    @Transactional
    public StoryAiLog createLog(StoryAiLog storyAiLog) {
        return storyAiLogRepository.save(storyAiLog);
    }
}
