package com.owing.entity.domains.story.repository;

import org.springframework.stereotype.Repository;

import com.owing.entity.dnd.file.repository.BaseFileRepository;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@Repository
public interface StoryRepositoryBase extends BaseFileRepository<Story, StoryFolder> {
}
