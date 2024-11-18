package com.owing.core.dnd.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.core.dnd.folder.model.BaseFolderEntity;

@NoRepositoryBean
public interface BaseFolderEntityRepository<T extends BaseFolderEntity> extends BaseFolderRepository<T>, JpaRepository<T, Long> {
	//
	// @Query("select f from #{#entityName} f where f.projectId = :projectId and f.deleted = false order by f.position")
	// List<T> findByParentId(Long projectId);
	//
	// @Modifying
	// @Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.projectId = :projectId and T.deleted = false")
	// void decrementPositionAfter(Long position, Long projectId);
	//
	// @Query("SELECT COALESCE(MAX(T.position), '-1') FROM #{#entityName} T WHERE T.projectId = :projectId and T.deleted = false")
	// Long getMaxPositionByParentId(Long projectId);
	//
	// @Modifying
	// @Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.projectId = :projectId and T.deleted = false")
	// void decrementPositionBetween(Long start, Long end, Long projectId);
	//
	// @Modifying
	// @Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.projectId = :projectId and T.deleted = false")
	// void incrementPositionBetween(Long start, Long end, Long projectId);

}
