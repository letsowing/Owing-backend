package com.owing.core.dnd.service.shift;

import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.service.DndService;

public abstract class DndShiftService<T extends Dnd> implements DndService<T> {
	protected abstract DndShiftAdapter<T> dndAdapter();

	public T create(T entity) {
		long position = dndAdapter().getNewPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndAdapter().save(entity);
	}

	public T delete(T entity) {
		dndAdapter().shiftFolderUp(entity.getPosition(), entity.getParentId()); // 엔티티 삭제 후 reorder

		dndAdapter().delete(entity);
		return entity;
	}

	/** 엔티티 위치 변경 */
	public T updatePosition(T entity, T before, T after, DndFolder parent) {
		if (!ShiftValidator.validatePosition(entity, before, after, parent)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}

		if (ShiftValidator.isMoveInSameFolder(entity, before, after, parent)) { // 같은 폴더 내 위치 이동
			long newPosition = getUpdatePosition(entity, before, after);
			moveInSameParent(entity, newPosition);
		} else {
			long newPosition = getUpdatePosition(entity, before, after, parent);
			moveInDifferentParent((DndFile)entity, parent, newPosition);
		}

		return dndAdapter().updatePosition(entity);
	}


	private void moveInSameParent(T entity, long newPosition) {
		if (entity.getPosition() < newPosition) {
			dndAdapter().moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			dndAdapter().moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}
		entity.updatePosition(newPosition);
	}

	private void moveInDifferentParent(DndFile entity, DndFolder parent, long newPosition) {
		dndAdapter().shiftFolderUp(entity.getPosition(), entity.getParentId());
		dndAdapter().shiftFolderDown(newPosition, parent.getId());
		entity.updateFolder(parent);
		entity.updatePosition(newPosition);
	}


	/** 새로운 위치 */
	private long getUpdatePosition(T entity, T before, T after) {
		if (before != null) {
			return before.isAfter(entity) ? before.getPosition() : before.getPosition() + 1;
		} else if (after != null) {
			return after.isAfter(entity) ? after.getPosition() - 1 : after.getPosition();
		}
		return entity.getPosition();
	}

	/** 새로운 위치 */
	private long getUpdatePosition(T entity, T before, T after, DndFolder parent) {
		if (before == null && after == null) {
			return dndAdapter().getNewPosition(parent.getId());
		}
		return before != null ? before.getPosition() + 1 : after.getPosition();
	}

}
