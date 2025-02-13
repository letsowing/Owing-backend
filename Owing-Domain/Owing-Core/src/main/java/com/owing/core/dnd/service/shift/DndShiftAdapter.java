package com.owing.core.dnd.service.shift;

import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.Dnd;

public abstract class DndShiftAdapter<T extends Dnd> extends DndAdapter<T> {
	protected abstract DndShiftRepository<T> dndRepository();

	public long getNewPosition(Long parentId){
		return dndRepository().getMaxPositionByParentId(parentId) + 1;
	}

	/** 다른 폴더 - 옮긴 후 바뀐 위치의 밑에 있는 파일들을 아래로 내린다.(+1) */
	protected void shiftFolderDown(long targetPosition, Long folderId) {
		dndRepository().incrementPositionAfter(targetPosition, folderId);
	}

	/** 다른 폴더 - 옮기기 전 기존 폴더 위치의 밑에 있는 파일들을 위로 올린다. (-1) */
	public void shiftFolderUp(long position, Long parentId) {
		dndRepository().decrementPositionAfter(position, parentId);
	}

	/** 동일 폴더 - 아래로 이동 : 이전 위치 ~ 바뀐 위치 사이 파일들을 위로 올린다. (-1) */
	public void moveFolderDown(long newPosition, long oldPosition, Long parentId) {
		dndRepository().decrementPositionBetween(oldPosition + 1, newPosition, parentId);
	}

	/** 동일 폴더 - 위로 이동 : 바뀐 위치 ~ 이전 위치 사이 파일들을 아래로 내린다. (+1) */
	public void moveFolderUp(long newPosition, long oldPosition, Long parentId) {
		dndRepository().incrementPositionBetween(newPosition, oldPosition - 1, parentId);
	}
}
