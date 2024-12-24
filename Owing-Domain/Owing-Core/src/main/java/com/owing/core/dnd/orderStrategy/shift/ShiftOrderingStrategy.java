package com.owing.core.dnd.orderStrategy.shift;

import java.util.Objects;

import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ShiftOrderingStrategy<T extends Dnd> implements OrderingStrategy<T> {
	protected abstract DndAdapter<T> dndAdapter();

	@Override
	public long getNewPosition(Long parentId) {
		return getLastPosition(parentId) + 1;
	}

	public long getUpdatePosition(T entity, T beforeEntity, T afterEntity) {
		if(beforeEntity != null){
			if (beforeEntity.getParentId().equals(entity.getParentId()) && beforeEntity.getPosition() > entity.getPosition()) { // 현재 위치보다 아래로
				return beforeEntity.getPosition();
			}
			return beforeEntity.getPosition() + 1; 		// 현재 위치보다 위로
		} else {
			if (afterEntity.getParentId().equals(entity.getParentId()) && afterEntity.getPosition() > entity.getPosition()){
				return afterEntity.getPosition() - 1;
			}
			return afterEntity.getPosition();
		}
	}

	protected abstract T handleEntityUpdate(T entity, T beforeEntity, T afterEntity);

	@Override
	public void reorderEntity(T entity) {
		shiftFolderUp(entity.getPosition(), entity.getParentId());
	}

	@Override
	public T updatePosition(T entity, T beforeEntity, T afterEntity) {
		if (!validateEntityPosition(entity, beforeEntity, afterEntity)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}
		return handleEntityUpdate(entity, beforeEntity, afterEntity);
	}

	protected abstract boolean validateEntityPosition(T entity, T beforeEntity, T afterEntity);

	protected void moveFolderUp(long newPosition, long oldPosition, Long projectId) {
		dndAdapter().incrementPositionBetween(newPosition, oldPosition - 1, projectId);
	}

	protected void moveFolderDown(long newPosition, long oldPosition, Long projectId) {
		dndAdapter().decrementPositionBetween(oldPosition + 1, newPosition, projectId);
	}

	protected void shiftFolderUp(long targetPosition, Long projectId) {
		dndAdapter().decrementPositionAfter(targetPosition, projectId);
	}

	protected long getLastPosition(Long projectId) {
		return dndAdapter().getMaxPositionByParentId(projectId);
	}

	protected boolean hasSameParentFolder(T e1, T e2) {
		return Objects.equals(e1.getParentId(), e2.getParentId());
	}

	protected boolean isSequentialPosition(T beforeEntity, T afterEntity) {
		return afterEntity.getPosition() - beforeEntity.getPosition() == 1;
	}

}
