package com.owing.core.dnd.base.orderStrategy.shift;

import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.core.dnd.folder.repository.BaseFolderRepository;

public abstract class FolderShiftOrderingStrategy<T extends BaseFolder> extends ShiftOrderingStrategy<T>{

	public FolderShiftOrderingStrategy(BaseFolderAdapter<T> dndAdapter, BaseFolderRepository<T> dndRepository) {
		super(dndAdapter, dndRepository);
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
		return dndRepository.save(entity);
	}
}
