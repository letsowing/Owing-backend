package com.owing.core.dnd.service.shift.repository;

import com.owing.core.dnd.model.DndFile;

public interface FileShiftRepository <T extends DndFile> extends DndShiftRepository<T> {
	void incrementPositionAfter(Long targetPosition, Long projectId);
}
