package com.owing.entity.domains.ai.log.story.repository;

import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.story.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpellCheckLogRepository extends JpaRepository<SpellCheckLog, Long> {

    List<SpellCheckLog> findByStoryOrderByCreatedAtAsc(Story story);

}
