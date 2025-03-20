package com.owing.entity.domains.ai.log.story.adapter;

import java.util.List;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.ai.log.story.repository.CrashCheckLogRepository;
import com.owing.entity.domains.story.model.Story;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CrashCheckLogAdapter {

    private final CrashCheckLogRepository crashCheckLogRepository;

    public List<CrashCheckLog> findByStoryId(Story story) {
        return crashCheckLogRepository.findByStoryOrderByCreatedAtAsc(story);
    }

    public CrashCheckLog save(CrashCheckLog crashCheckLog) {
        return crashCheckLogRepository.save(crashCheckLog);
    }

}
