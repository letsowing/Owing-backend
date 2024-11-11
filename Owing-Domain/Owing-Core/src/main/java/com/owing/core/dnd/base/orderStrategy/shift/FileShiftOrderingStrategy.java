package com.owing.core.dnd.base.orderStrategy.shift;

import java.util.Objects;

import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.core.dnd.file.model.BaseFileEntity;
import com.owing.core.dnd.file.repository.BaseFileEntityRepository;
import com.owing.core.dnd.folder.model.BaseFolderEntity;

public abstract class FileShiftOrderingStrategy<T extends BaseFileEntity<F>, F extends BaseFolderEntity> extends ShiftOrderingStrategy<T>{

	public FileShiftOrderingStrategy(BaseFileAdapter<T> dndAdapter, BaseFileEntityRepository<T, F> dndRepository) {
		super(dndAdapter, dndRepository);
	}

	protected boolean validateEntityPosition(T entity, T beforeEntity, T afterEntity) {
		if (beforeEntity == null && afterEntity == null) {
			return false;
		}
		if (beforeEntity != null && afterEntity != null){
			return hasSameParentFolder(beforeEntity, afterEntity) && isSequentialPosition(beforeEntity, afterEntity);
		}
		return true;
	}

	private F getParentFolder(T beforeEntity, T afterEntity) {
		return beforeEntity!= null? beforeEntity.getFolder() : afterEntity.getFolder();
	}

	@Override
	protected T handleEntityUpdate(T entity, T beforeEntity, T afterEntity) {
		long newPosition = getUpdatePosition(entity, beforeEntity, afterEntity);
		F newParent = getParentFolder(beforeEntity, afterEntity);

		if(Objects.equals(entity.getFolder(), newParent)){ // fixme
			updatePositionInSameFolder(entity, newPosition);
		}else {
			updatePositionInDifferentFolder(entity, newPosition, entity.getFolder(), newParent);
		}
		entity.updatePosition(newPosition);
		return dndRepository.save(entity);
	}

	private void updatePositionInSameFolder(T entity, long newPosition){
		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}

	}

	private void updatePositionInDifferentFolder(T entity, long newPosition, F oldFolder, F newFolder){
        shiftFolderUp(entity.getPosition(), oldFolder.getId());
		shiftFolderDown(newPosition, newFolder.getId());
		entity.updateFolder(newFolder);
    }
}
