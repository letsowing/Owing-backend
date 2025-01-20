package com.owing.core.dnd.orderStrategy.shift.repository;

import com.owing.core.dnd.base.model.DndFile;

public interface FileShiftRepository <T extends DndFile> extends DndShiftRepository<T> {
	void incrementPositionAfter(Long targetPosition, Long projectId);
}
