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
        -- story의 deleted 상태를 업데이트
        UPDATE story s
        SET deleted = false
        FROM story_folder sf
        WHERE s.folder_id = sf.id
          AND s.id = :itemId;

        -- story_folder의 deleted 상태를 업데이트
        UPDATE story_folder sf
        SET deleted = false
        WHERE sf.id = (
          SELECT s.folder_id
          FROM story s
          WHERE s.id = :itemId
        );

        -- storyContent의 deleted 상태를 업데이트
        UPDATE story_content sc
        SET deleted = false
        WHERE sc.id = (
          SELECT s.story_content_id
          FROM story s
          WHERE s.id = :itemId
        );
        """, nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);

	List<Story> findByFolder_ProjectId(Long projectId);

	@Query(value = "select * from story where folder_id = :folderId and deleted = false order by position", nativeQuery = true)
	List<Story> findByParentId(Long folderId);

	@Modifying
	@Query(value = "update story set position = position - 1 where position > :position and folder_id = :folderId and deleted = false", nativeQuery = true)
	void decrementPositionAfter(Long position, Long folderId);

	@Modifying
	@Query(value = "update story set position = position + 1 where position >= :position and folder_id = :folderId and deleted = false", nativeQuery = true)
	void incrementPositionAfter(Long position, Long folderId);

	@Query(value = "SELECT COALESCE(MAX(position), '-1') FROM story WHERE folder_id = :folderId and deleted = false", nativeQuery = true)
	Long getMaxPositionByParentId(Long folderId);

	@Modifying
	@Query(value = "update story set position = position - 1 where position between :start and :end and folder_id = :folderId and deleted = false", nativeQuery = true)
	void decrementPositionBetween(Long start, Long end, Long folderId);

	@Modifying
	@Query(value = "update story set position = position + 1 where position between :start and :end and folder_id = :folderId and deleted = false", nativeQuery = true)
	void incrementPositionBetween(Long start, Long end, Long folderId);

}
