package com.owing.core.dnd.base.orderStrategy.shift;

import java.util.Objects;

import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.error.DndErrorCode;
import com.owing.core.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ShiftOrderingStrategy<T extends BaseDnd> implements OrderingStrategy<T> {
	protected final BaseDndAdapter<T> dndAdapter;
	protected final BaseDndRepository<T> dndRepository;

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

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, BaseDnd newParent) {
		return updatePosition(dndEntity, beforeEntity, afterEntity);
	}

	protected abstract boolean validateEntityPosition(T entity, T beforeEntity, T afterEntity);

	protected void moveFolderUp(long newPosition, long oldPosition, Long projectId) {
		dndAdapter.incrementPositionBetween(newPosition, oldPosition - 1, projectId);
	}

	protected void moveFolderDown(long newPosition, long oldPosition, Long projectId) {
		dndAdapter.decrementPositionBetween(oldPosition + 1, newPosition, projectId);
	}

	protected void shiftFolderUp(long targetPosition, Long projectId) {
		dndAdapter.decrementPositionAfter(targetPosition, projectId);
	}

	protected void shiftFolderDown(long targetPosition, Long projectId) {
		dndAdapter.decrementPositionAfter(targetPosition, projectId);
	}

	protected long getLastPosition(Long projectId) {
		return dndRepository.getMaxPositionByParentId(projectId);
	}

	protected boolean hasSameParentFolder(T e1, T e2) {
		return Objects.equals(e1.getParentId(), e2.getParentId());
	}

	protected boolean isSequentialPosition(T beforeEntity, T afterEntity) {
		return afterEntity.getPosition() - beforeEntity.getPosition() == 1;
	}

}
