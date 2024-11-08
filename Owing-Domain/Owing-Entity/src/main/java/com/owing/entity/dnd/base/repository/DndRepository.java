package com.owing.entity.dnd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import com.owing.entity.dnd.base.model.DndEntity;

@NoRepositoryBean
@Component
public interface DndRepository<T extends DndEntity> extends JpaRepository<T, Long> {
	// Optional<T> findById(Long id);

	List<T> findByParentId(Long parentId);
	// @Modifying
	// @Query("update #{#entityName} T set T.position = T.position - 1 where T.position > :position and T.projectId = :projectId")
	void decrementPositionAfter(Long position, Long projectId);

	// @Modifying
	// @Query("update  #{#entityName} T set T.position = T.position - 1 where T.position between :start and :end and T.projectId = :projectId")
	void decrementPositionBetween(Long start, Long end, Long projectId);

	// @Modifying
	// @Query("update #{#entityName} T set T.position = T.position + 1 where T.position between :start and :end and T.projectId = :projectId")
	void incrementPositionBetween(Long start, Long end, Long projectId);

	// List<T> findAllByParentId(Long parentId);

	Long getMaxPositionByParentId(Long parentId);
}
