package com.owing.entity.domains.story.repository;

import org.springframework.stereotype.Repository;

import com.owing.core.dnd.file.repository.BaseFileEntityRepository;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@Repository
public interface StoryRepository extends BaseFileEntityRepository<Story, StoryFolder> {
}
