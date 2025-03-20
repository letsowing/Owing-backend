package com.owing.entity.domains.universe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owing.entity.dnd.file.repository.DndFileEntityRepository;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;

public interface UniverseRepository extends DndFileEntityRepository<Universe> {
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

	@Query(value = "select image_url from universe where id = :id and deleted = false", nativeQuery = true)
    String findImageUrlById(Long id);
}
