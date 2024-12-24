package com.owing.core.dnd.orderStrategy.shift;

import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.folder.adapter.DndFolderAdapter;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class FolderShiftOrderingStrategy<T extends DndFolder> extends ShiftOrderingStrategy<T>{

	protected abstract DndFolderAdapter<T> dndAdapter();

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, Dnd newParent) {
		return updatePosition(dndEntity, beforeEntity, afterEntity);
	}

	protected boolean validateEntityPosition(T entity, T beforeEntity, T afterEntity) {
		if (beforeEntity == null && afterEntity == null) {
			return false;
		}

		if (beforeEntity == null) {
			// isFirstPosition(afterEntity);
			return hasSameParentFolder(entity, afterEntity);
		}
		if (afterEntity == null) {
			// isLastPosition(beforeEntity);
			return hasSameParentFolder(entity, beforeEntity);
		}
		return isSequentialPosition(beforeEntity, afterEntity);
	}

	@Override
	protected T handleEntityUpdate(T entity, T beforeEntity, T afterEntity) {
		long newPosition = getUpdatePosition(entity, beforeEntity, afterEntity);

		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}
		entity.updatePosition(newPosition);
		return dndAdapter().updatePosition(entity);
	}
}
