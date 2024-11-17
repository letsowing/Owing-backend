package com.owing.entity.domains.ai.log.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.domains.ai.log.story.adapter.CrashCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.ai.log.story.repository.CrashCheckLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrashCheckLogDomainService {

    private final CrashCheckLogAdapter crashCheckLogAdapter;
    private final CrashCheckLogRepository storyCrashLogRepository;

    @Transactional
    public CrashCheckLog createLog(CrashCheckLog crashCheckLog) {
        return storyCrashLogRepository.save(crashCheckLog);
    }
}
