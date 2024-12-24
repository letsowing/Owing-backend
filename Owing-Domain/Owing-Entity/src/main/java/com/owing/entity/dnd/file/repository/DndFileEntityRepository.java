package com.owing.entity.dnd.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.core.dnd.file.repository.DndFileRepository;
import com.owing.entity.dnd.file.model.DndFileEntity;

@NoRepositoryBean
public interface DndFileEntityRepository<T extends DndFileEntity> extends DndFileRepository<T>, JpaRepository<T, Long> {

	@Query(value = "select f from #{#entityName} f where f.folder.id = :folderId and f.deleted = false order by f.position")
	List<T> findByParentId(Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.folder.id = :folderId and T.deleted = false")
	void decrementPositionAfter(Long position, Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position >= :position and T.folder.id = :folderId and T.deleted = false")
	void incrementPositionAfter(Long position, Long folderId);

	@Query("SELECT COALESCE(MAX(T.position), '-1') FROM #{#entityName} T WHERE T.folder.id = :folderId and T.deleted = false")
	Long getMaxPositionByParentId(Long folderId);

	@Modifying
	@Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.folder.id = :folderId and T.deleted = false")
	void decrementPositionBetween(Long start, Long end, Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.folder.id = :folderId and T.deleted = false")
	void incrementPositionBetween(Long start, Long end, Long folderId);
}
