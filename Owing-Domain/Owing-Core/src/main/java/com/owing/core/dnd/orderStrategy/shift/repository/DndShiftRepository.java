package com.owing.core.dnd.orderStrategy.shift.repository;

import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.base.repository.DndRepository;

public interface DndShiftRepository<T extends Dnd> extends DndRepository<T> {
	void decrementPositionAfter(Long position, Long projectId);
	void decrementPositionBetween(Long start, Long end, Long projectId);
	void incrementPositionBetween(Long start, Long end, Long projectId);
	Long getMaxPositionByParentId(Long parentId);
}
