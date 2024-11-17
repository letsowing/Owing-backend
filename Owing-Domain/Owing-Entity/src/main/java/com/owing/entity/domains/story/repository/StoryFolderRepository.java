package com.owing.entity.domains.story.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.owing.core.dnd.folder.repository.BaseFolderEntityRepository;
import com.owing.entity.domains.story.model.StoryFolder;

@Repository
public interface StoryFolderRepository extends BaseFolderEntityRepository<StoryFolder> {

	@Modifying
	@Query(value = "UPDATE story_folder SET deleted = false WHERE id = :itemId", nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);
}
