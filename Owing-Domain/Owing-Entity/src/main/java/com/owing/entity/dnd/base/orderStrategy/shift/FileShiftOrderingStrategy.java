package com.owing.entity.dnd.base.orderStrategy.shift;

import java.util.Objects;

import com.owing.entity.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.file.repository.BaseFileRepository;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

public abstract class FileShiftOrderingStrategy<T extends BaseFileEntity> extends ShiftOrderingStrategy<T>{

	public FileShiftOrderingStrategy(BaseFileAdapter<T> dndAdapter, BaseFileRepository<T> dndRepository) {
		super(dndAdapter, dndRepository);
	}

	@Override
	protected T handleEntityUpdate(T entity, long newPosition) {
		if(Objects.equals(entity.getFolder().getId(), 1)){ // fixme
			updatePositionInSameFolder(entity, newPosition);
		}else {
			updatePositionInDifferentFolder(entity, newPosition, entity.getFolder(), entity.getFolder());
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

	private void updatePositionInDifferentFolder(T entity, long newPosition, BaseFolderEntity oldFolder, BaseFolderEntity newFolder){
        shiftFolderUp(entity.getPosition(), entity.getFolder().getId());
		shiftFolderDown(newPosition, newFolder.getId());
		entity.updateFolder(newFolder);
    }
}
