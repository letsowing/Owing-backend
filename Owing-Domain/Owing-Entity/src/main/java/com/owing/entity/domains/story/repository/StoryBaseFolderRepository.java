package com.owing.entity.domains.story.repository;

import org.springframework.stereotype.Repository;

import com.owing.entity.dnd.folder.repository.BaseFolderRepository;
import com.owing.entity.domains.story.model.StoryFolder;

@Repository
public interface StoryBaseFolderRepository extends BaseFolderRepository<StoryFolder> {
}
