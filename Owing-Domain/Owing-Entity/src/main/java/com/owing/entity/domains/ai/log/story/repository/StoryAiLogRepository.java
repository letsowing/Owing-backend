package com.owing.entity.domains.ai.log.story.repository;

import com.owing.entity.domains.ai.log.story.model.StoryAiLog;
import com.owing.entity.domains.story.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryAiLogRepository extends JpaRepository<StoryAiLog, Long> {

    List<StoryAiLog> findByStory(Story story);

}
