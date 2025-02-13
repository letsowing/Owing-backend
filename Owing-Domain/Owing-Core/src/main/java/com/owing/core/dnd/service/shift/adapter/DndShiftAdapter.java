package com.owing.core.dnd.service.shift.adapter;

import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.service.shift.repository.DndShiftRepository;

public abstract class DndShiftAdapter<T extends Dnd> extends DndAdapter<T> {
	protected abstract DndShiftRepository<T> dndRepository();


	public long getMaxPositionByParentId(Long parentId){
		return dndRepository().getMaxPositionByParentId(parentId);
	}

	public void decrementPositionAfter(long position, Long parentId) {
		dndRepository().decrementPositionAfter(position, parentId);
	}

	public void decrementPositionBetween(long fromPosition, long toPosition, Long parentId) {
		dndRepository().decrementPositionBetween(fromPosition, toPosition, parentId);
	}

	public void incrementPositionBetween(long fromPosition, long toPosition, Long parentId) {
		dndRepository().incrementPositionBetween(fromPosition, toPosition, parentId);
	}
}
