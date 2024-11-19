package com.owing.entity.domains.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owing.core.dnd.file.repository.BaseFileEntityRepository;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;

public interface UniverseRepository extends BaseFileEntityRepository<Universe, UniverseFolder> {
	@Modifying
	@Query(value = """
        -- universe의 deleted 상태를 업데이트
        UPDATE universe u
        SET deleted = false
        FROM universe_folder uf
        WHERE u.folder_id = uf.id
          AND u.id = :itemId;

        -- universe_folder의 deleted 상태를 업데이트
        UPDATE universe_folder uf
        SET deleted = false
        WHERE uf.id = (
          SELECT u.folder_id
          FROM universe u
          WHERE u.id = :itemId
        )
        """, nativeQuery = true)
	void restoreById(@Param("itemId") Long itemId);

	List<Universe> findByFolder_ProjectId(Long projectId);

	@Query(value = "select * from universe where folder_id = :folderId and deleted = false order by position", nativeQuery = true)
	List<Universe> findByParentId(Long folderId);

	@Modifying
	@Query(value = "update universe set position = position - 1 where position > :position and folder_id = :folderId and deleted = false", nativeQuery = true)
	void decrementPositionAfter(Long position, Long folderId);

	@Modifying
	@Query(value = "update universe set position = position + 1 where position >= :position and folder_id = :folderId and deleted = false", nativeQuery = true)
	void incrementPositionAfter(Long position, Long folderId);

	@Query(value = "SELECT COALESCE(MAX(position), '-1') FROM story WHERE folder_id = :folderId and deleted = false", nativeQuery = true)
	Long getMaxPositionByParentId(Long folderId);

	@Modifying
	@Query(value = "update universe set position = position - 1 where position between :start and :end and folder_id = :folderId and deleted = false", nativeQuery = true)
	void decrementPositionBetween(Long start, Long end, Long folderId);

	@Modifying
	@Query(value = "update universe set position = position + 1 where position between :start and :end and folder_id = :folderId and deleted = false", nativeQuery = true)
	void incrementPositionBetween(Long start, Long end, Long folderId);

	@Query(value = "select image_url from universe where id = :id and deleted = false", nativeQuery = true)
    String findImageUrlById(Long id);
}
