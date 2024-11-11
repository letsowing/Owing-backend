package com.owing.entity.domains.story.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.owing.core.dnd.file.repository.BaseFileEntityRepository;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@Repository
public interface StoryRepository extends BaseFileEntityRepository<Story, StoryFolder> {

	@Modifying
	@Query(value = "UPDATE story SET deleted = false WHERE id = :itemId", nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);
}
