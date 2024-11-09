package com.owing.entity.dnd.base.orderStrategy.shift;

import com.owing.entity.dnd.base.adapter.BaseDndAdapter;
import com.owing.entity.dnd.base.error.DndErrorCode;
import com.owing.entity.dnd.base.error.exception.DndInvalidPositionException;
import com.owing.entity.dnd.base.model.BaseDnd;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.entity.dnd.base.repository.BaseDndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ShiftOrderingStrategy<T extends BaseDnd> implements OrderingStrategy<T> {
	protected final BaseDndAdapter<T> dndAdapter;
	protected final BaseDndRepository<T> dndRepository;

	@Override
	public long getNewEntityPosition(Long parentId){
		return getLastPosition(parentId) + 1;
	}

	protected abstract T handleEntityUpdate(T entity, long newPosition);

	@Override
	public void reorderEntity(T entity){
		shiftFolderUp(entity.getPosition(), entity.getParentId());
	}

	@Override
	public T updatePosition(T entity, long newPosition) {
		long maxPosition = getLastPosition(entity.getParentId());
		if(!validateEntityPosition(newPosition, maxPosition)){
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}

		return handleEntityUpdate(entity, newPosition);
	}

	protected boolean validateEntityPosition(long newPosition, long maxPosition) {
		return newPosition <= maxPosition;
	}

	protected void moveFolderUp(long newPosition, long oldPosition, Long projectId){
		dndAdapter.incrementPositionBetween(newPosition, oldPosition - 1, projectId);
	}

	protected void moveFolderDown(long newPosition, long oldPosition, Long projectId){
		dndAdapter.decrementPositionBetween(oldPosition + 1, newPosition, projectId);
	}

	protected void shiftFolderUp(long targetPosition, Long projectId){
		dndAdapter.decrementPositionAfter(targetPosition, projectId);
	}

	protected void shiftFolderDown(long targetPosition, Long projectId){
		dndAdapter.decrementPositionAfter(targetPosition, projectId);
	}

	protected long getLastPosition(Long projectId){
		return dndRepository.getMaxPositionByParentId(projectId);
	}


}
