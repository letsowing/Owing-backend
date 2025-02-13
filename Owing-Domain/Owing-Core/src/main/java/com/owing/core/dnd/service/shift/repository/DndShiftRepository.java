package com.owing.core.dnd.service.shift.repository;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.repository.DndRepository;

public interface DndShiftRepository<T extends Dnd> extends DndRepository<T> {
	void decrementPositionAfter(Long position, Long projectId);
	void decrementPositionBetween(Long start, Long end, Long projectId);
	void incrementPositionBetween(Long start, Long end, Long projectId);
	Long getMaxPositionByParentId(Long parentId);
}
