package com.owing.core.dnd.service.shift;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.service.DndService;
import com.owing.core.dnd.service.shift.adapter.DndShiftAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ShiftDndService<T extends Dnd> implements DndService<T> {
	protected abstract DndShiftAdapter<T> dndAdapter();

	/** 새로운 위치  */
	@Override
	public long getNewPosition(Long parentId) {
		return dndAdapter().getMaxPositionByParentId(parentId) + 1;
	}

	/** 엔티티 삭제 후 reorder */
	@Override
	public void reorderEntity(T entity) {
		shiftFolderUp(entity.getPosition(), entity.getParentId());
	}

	/** 새로운 위치 */
	public long getUpdatePosition(T entity, T before, T after, Dnd newParent) {
		if (before == null && after == null) {
			return getNewPosition(newParent.getId());
		}
		if (before != null) {
			if (before.isInSameParent(entity) && before.isAfter(entity)) { // 현재 위치보다 아래로
				return before.getPosition();
			}
			return before.getPosition() + 1;        // 현재 위치보다 위로
		} else {
			if (after.isInSameParent(entity) && after.isAfter(entity)) {
				return after.getPosition() - 1;
			}
			return after.getPosition();
		}
	}

	/** 동일 폴더 - 위로 이동 : 바뀐 위치 ~ 이전 위치 사이 파일들을 아래로 내린다. (+1) */
	protected void moveFolderUp(long newPosition, long oldPosition, Long parentId) {
		dndAdapter().incrementPositionBetween(newPosition, oldPosition - 1, parentId);
	}

	/** 동일 폴더 - 아래로 이동 : 이전 위치 ~ 바뀐 위치 사이 파일들을 위로 올린다. (-1) */
	protected void moveFolderDown(long newPosition, long oldPosition, Long parentId) {
		dndAdapter().decrementPositionBetween(oldPosition + 1, newPosition, parentId);
	}

	/** 다른 폴더 - 옮기기 전 기존 폴더 위치의 밑에 있는 파일들을 위로 올린다. (-1) */
	protected void shiftFolderUp(long targetPosition, Long parentId) {
		dndAdapter().decrementPositionAfter(targetPosition, parentId);
	}

	/** 같은 폴더에서 위치바꾸기 */
	protected void updatePositionInSameFolder(T entity, long newPosition) {
		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}
	}

	/** 붙어있는지 */
	protected boolean isSequentialPosition(T before, T after) {
		return after.getPosition() - before.getPosition() == 1;
	}

	public T create(T entity) {
		long position = getNewPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndAdapter().save(entity);
	}

	public T delete(T entity) {
		reorderEntity(entity);
		dndAdapter().delete(entity);
		return entity;
	}

}
