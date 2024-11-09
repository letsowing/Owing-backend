package com.owing.core.dnd.base.orderStrategy.shift;

import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.core.dnd.folder.repository.BaseFolderRepository;

public abstract class FolderShiftOrderingStrategy<T extends BaseFolder> extends ShiftOrderingStrategy<T>{

	public FolderShiftOrderingStrategy(BaseFolderAdapter<T> dndAdapter, BaseFolderRepository<T> dndRepository) {
		super(dndAdapter, dndRepository);
	}

	@Override
	protected T handleEntityUpdate(T entity, long newPosition) {
		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}
		entity.updatePosition(newPosition);
		return dndRepository.save(entity);
	}
}
