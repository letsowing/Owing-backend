package com.owing.entity.domains.story.repository;

import org.springframework.stereotype.Repository;

import com.owing.core.dnd.folder.repository.BaseFolderEntityRepository;
import com.owing.entity.domains.story.model.StoryFolder;

@Repository
public interface StoryFolderRepository extends BaseFolderEntityRepository<StoryFolder> {
}
