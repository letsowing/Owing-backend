package com.owing.entity.domains.story.repository;

import java.util.List;

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

	@Query(value = "select * from story_folder where project_id = :projectId and deleted = false order by position", nativeQuery = true)
	List<StoryFolder> findByParentId(Long projectId);

	@Modifying
	@Query(value = "update story_folder set position = position - 1 where position > :position and project_id = :projectId and deleted = false", nativeQuery = true)
	void decrementPositionAfter(Long position, Long projectId);

	@Query(value = "SELECT COALESCE(MAX(position), '-1') FROM story_folder WHERE project_id = :projectId and deleted = false", nativeQuery = true)
	Long getMaxPositionByParentId(Long projectId);

	@Modifying
	@Query(value = "update story_folder set position = position - 1 where position between :start and :end and project_id = :projectId and deleted = false", nativeQuery = true)
	void decrementPositionBetween(Long start, Long end, Long projectId);

	@Modifying
	@Query(value = "update story_folder set position = position + 1 where position between :start and :end and project_id = :projectId and deleted = false", nativeQuery = true)
	void incrementPositionBetween(Long start, Long end, Long projectId);

}
