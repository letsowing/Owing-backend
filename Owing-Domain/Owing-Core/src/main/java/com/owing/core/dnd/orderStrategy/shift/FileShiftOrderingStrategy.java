package com.owing.core.dnd.orderStrategy.shift;

import java.util.Objects;

import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.file.adapter.DndFileAdapter;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class FileShiftOrderingStrategy<T extends DndFile> extends ShiftOrderingStrategy<T>{

	@Override
	protected abstract DndFileAdapter<T> dndAdapter();

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
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, Dnd newParent) {
		if(beforeEntity == null && afterEntity == null){
			long newPosition = getNewPosition(newParent.getId());
			updatePositionInDifferentFolder(dndEntity, newPosition, dndEntity.getFolder(), (DndFolder)newParent);
			dndEntity.updatePosition(newPosition);
			System.out.println(dndEntity.getParentId());
			return dndAdapter().updatePosition(dndEntity);
		} else {
			return updatePosition(dndEntity, beforeEntity, afterEntity);
		}

	}

	private DndFolder getParentFolder(T beforeEntity, T afterEntity) {
		return beforeEntity!= null? beforeEntity.getFolder() : afterEntity.getFolder();
	}

	@Override
	protected T handleEntityUpdate(T entity, T beforeEntity, T afterEntity) {
		long newPosition = getUpdatePosition(entity, beforeEntity, afterEntity);
		DndFolder newParent = getParentFolder(beforeEntity, afterEntity);

		if(Objects.equals(entity.getParentId(), newParent.getId())){ // fixme
			updatePositionInSameFolder(entity, newPosition);
		}else {
			updatePositionInDifferentFolder(entity, newPosition, entity.getFolder(), newParent);
		}
		entity.updatePosition(newPosition);
		return dndAdapter().updatePosition(entity);
	}

	private void updatePositionInSameFolder(T entity, long newPosition){
		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}

	}

	private void updatePositionInDifferentFolder(T entity, long newPosition, DndFolder oldFolder, DndFolder newFolder){
        shiftFolderUp(entity.getPosition(), oldFolder.getId());
		shiftFolderDown(newPosition, newFolder.getId());
		entity.updateFolder(newFolder);
    }

	protected void shiftFolderDown(long targetPosition, Long projectId) {
		dndAdapter().incrementPositionAfter(targetPosition, projectId);
	}
}
