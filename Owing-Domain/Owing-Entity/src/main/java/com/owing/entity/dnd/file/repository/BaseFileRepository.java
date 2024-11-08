package com.owing.entity.dnd.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.entity.dnd.base.repository.DndRepository;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

@NoRepositoryBean
public interface BaseFileRepository<T extends BaseFileEntity<F>, F extends BaseFolderEntity> extends DndRepository<T> {
	@Query(value = "select f from #{#entityName} f where f.folder_id = :folderId order by f.position", nativeQuery = true)
	List<T> findByParentId(Long folderId);

	List<T> findAllByFolderIdOrderByPositionAsc(Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.folder.id = :folderId")
	void decrementPositionAfter(Long position, Long folderId);

	@Query("SELECT COALESCE(MAX(T.position), 0) FROM #{#entityName} T WHERE T.folder.id = :folderId")
	Long getMaxPositionByParentId(Long folderId);

	@Modifying
	@Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.folder.id = :folderId")
	void decrementPositionBetween(Long start, Long end, Long folderId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.folder.id = :folderId")
	void incrementPositionBetween(Long start, Long end, Long folderId);
}
