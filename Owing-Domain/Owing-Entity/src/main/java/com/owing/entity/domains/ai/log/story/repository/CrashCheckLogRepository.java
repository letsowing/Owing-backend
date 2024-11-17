package com.owing.entity.domains.ai.log.story.repository;

import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.story.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrashCheckLogRepository extends JpaRepository<CrashCheckLog, Long> {

    List<CrashCheckLog> findByStoryOrderByCreatedAtAsc(Story story);

}
