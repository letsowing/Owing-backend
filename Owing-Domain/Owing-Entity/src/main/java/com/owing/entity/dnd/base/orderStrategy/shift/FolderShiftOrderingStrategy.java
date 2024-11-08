package com.owing.entity.dnd.base.orderStrategy.shift;

import com.owing.entity.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;
import com.owing.entity.dnd.folder.repository.BaseFolderRepository;

public abstract class FolderShiftOrderingStrategy<T extends BaseFolderEntity> extends ShiftOrderingStrategy<T>{

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
