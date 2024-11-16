package com.owing.core.dnd.base.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.owing.core.dnd.base.model.BaseDnd;

public interface BaseDndRepository<T extends BaseDnd> extends CrudRepository<T, Long> {
	List<T> findByParentId(Long parentId);
	void decrementPositionAfter(Long position, Long projectId);
	void decrementPositionBetween(Long start, Long end, Long projectId);
	void incrementPositionBetween(Long start, Long end, Long projectId);
	Long getMaxPositionByParentId(Long parentId);
	void restoreById(Long id);
	void incrementPositionAfter(Long targetPosition, Long projectId);
}
