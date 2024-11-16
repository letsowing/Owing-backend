package com.owing.core.dnd.base.orderStrategy.shift;

import java.util.Objects;

import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.file.repository.BaseFileRepository;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class FileShiftOrderingStrategy<T extends BaseFile<F>, F extends BaseFolder> extends ShiftOrderingStrategy<T>{

	private final BaseFileAdapter<T, F> baseFileAdapter;

	public FileShiftOrderingStrategy(BaseFileAdapter<T, F> dndAdapter, BaseFileRepository<T, F> dndRepository) {
		super(dndAdapter, dndRepository);
		this.baseFileAdapter = dndAdapter;
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

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, BaseDnd newParent) {
		if(beforeEntity == null && afterEntity == null){
			long newPosition = getNewPosition(newParent.getId());
			updatePositionInDifferentFolder(dndEntity, newPosition, dndEntity.getFolder(), (F)newParent);
			dndEntity.updatePosition(newPosition);
			System.out.println(dndEntity.getParentId());
			return dndAdapter.save(dndEntity);
		} else {
			return updatePosition(dndEntity, beforeEntity, afterEntity);
		}

	}

	private F getParentFolder(T beforeEntity, T afterEntity) {
		return beforeEntity!= null? beforeEntity.getFolder() : afterEntity.getFolder();
	}

	@Override
	protected T handleEntityUpdate(T entity, T beforeEntity, T afterEntity) {
		long newPosition = getUpdatePosition(entity, beforeEntity, afterEntity);
		F newParent = getParentFolder(beforeEntity, afterEntity);

		if(Objects.equals(entity.getParentId(), newParent.getId())){ // fixme
			updatePositionInSameFolder(entity, newPosition);
		}else {
			updatePositionInDifferentFolder(entity, newPosition, entity.getFolder(), newParent);
		}
		entity.updatePosition(newPosition);
		return dndAdapter.save(entity);
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

	protected void shiftFolderDown(long targetPosition, Long projectId) {
		this.baseFileAdapter.incrementPositionAfter(targetPosition, projectId);
	}
}
