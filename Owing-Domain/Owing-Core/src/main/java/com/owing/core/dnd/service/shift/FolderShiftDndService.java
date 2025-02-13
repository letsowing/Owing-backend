package com.owing.core.dnd.service.shift;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.service.shift.adapter.FolderShiftAdapter;

public abstract class FolderShiftDndService<T extends DndFolder> extends ShiftDndService<T> {

	protected abstract FolderShiftAdapter<T> dndAdapter();

	/** 엔티티 위치 변경 */
	@Override
	public T updatePosition(T entity, T before, T after, Dnd newParent) {
		if (!validatePosition(entity, before, after)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		long newPosition = getUpdatePosition(entity, before, after, null);

		updatePositionInSameFolder(entity, newPosition);
		entity.updatePosition(newPosition);
		return dndAdapter().updatePosition(entity);
	}

	protected boolean validatePosition(T entity, T before, T after) {
		if (before == null && after == null) {
			return false;
		}

		if (before == null) {
			// isFirstPosition(afterEntity);
			return entity.isInSameParent(after);
		}
		if (after == null) {
			// isLastPosition(beforeEntity);
			return entity.isInSameParent(before);
		}
		return isSequentialPosition(before, after);
	}

}
