package com.owing.core.dnd.service.shift.adapter;

import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.service.shift.repository.FileShiftRepository;

public abstract class FileShiftAdapter<T extends DndFile> extends DndShiftAdapter<T>{
	@Override
	protected abstract FileShiftRepository<T> dndRepository();

	public void incrementPositionAfter(long targetPosition, Long projectId) {
		dndRepository().incrementPositionAfter(targetPosition, projectId);
	}
}
