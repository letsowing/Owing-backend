package com.owing.entity.domains.story.repository;

import java.util.List;

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
	@Query(value = """
        UPDATE story s
        SET deleted = false
        FROM story_folder sf
        WHERE s.folder_id = sf.id
          AND s.id = :itemId
        """, nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);

	List<Story> findByFolder_ProjectId(Long projectId);

}
