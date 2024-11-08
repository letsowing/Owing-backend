package com.owing.entity.dnd.folder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.owing.entity.dnd.base.repository.DndRepository;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

@NoRepositoryBean
public interface BaseFolderRepository<T extends BaseFolderEntity> extends DndRepository<T> {
	@Query("select f from #{#entityName} f where f.projectId = :projectId order by f.position")
	List<T> findByParentId(Long projectId);

	List<T> findAllByProjectIdOrderByPositionAsc(Long projectId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.projectId = :projectId")
	void decrementPositionAfter(Long position, Long projectId);

	@Query("SELECT COALESCE(MAX(T.position), 0) FROM #{#entityName} T WHERE T.projectId = :projectId")
	Long getMaxPositionByParentId(Long projectId);

	@Modifying
	@Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.projectId = :projectId")
	void decrementPositionBetween(Long start, Long end, Long projectId);

	@Modifying
	@Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.projectId = :projectId")
	void incrementPositionBetween(Long start, Long end, Long projectId);

}
