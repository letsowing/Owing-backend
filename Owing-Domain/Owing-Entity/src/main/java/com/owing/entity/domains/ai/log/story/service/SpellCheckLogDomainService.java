package com.owing.entity.domains.ai.log.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.ai.log.story.adapter.SpellCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.ai.log.story.repository.SpellCheckLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpellCheckLogDomainService {

    private final SpellCheckLogAdapter spellCheckLogAdapter;
    private final SpellCheckLogRepository spellCheckLogRepository;

    @Transactional
    public SpellCheckLog createLog(SpellCheckLog spellCheckLog) {
        return spellCheckLogRepository.save(spellCheckLog);
    }
}
