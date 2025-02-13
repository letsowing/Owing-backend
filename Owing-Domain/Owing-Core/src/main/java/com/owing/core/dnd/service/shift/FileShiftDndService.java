package com.owing.core.dnd.service.shift;

import java.util.Objects;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndInvalidPositionException;
import com.owing.core.dnd.service.shift.adapter.FileShiftAdapter;

public abstract class FileShiftDndService<T extends DndFile> extends ShiftDndService<T> {

	@Override
	protected abstract FileShiftAdapter<T> dndAdapter();

	@Override
	public T updatePosition(T entity, T before, T after, Dnd newParent) {
		if (!validatePosition(entity, before, after, newParent)) {
			throw DndInvalidPositionException.of(DndErrorCode.INVALID_POSITION);
		}

		long newPosition = getUpdatePosition(entity, before, after, newParent);

		if (moveToSameFolder(entity, before, after, newParent)) { // 같은 폴더면
			updatePositionInSameFolder(entity, newPosition); // 같은 폴더에서 위치바꾸기
		} else {
			updatePositionInDifferentFolder(entity, newPosition, entity.getFolder(), (DndFolder)newParent); // 다른 폴더에서 위치바꾸기
		}
		entity.updatePosition(newPosition);
		return dndAdapter().updatePosition(entity);
	}

	protected boolean validatePosition(T entity, T before, T after, Dnd newParent) {
		if (before == null && after == null) {
			return newParent != null;
		}

		if (before != null && after != null) {
			boolean isSequential = isSequentialPosition(before, after) && before.isInSameParent(after);

			if (newParent == null)
				return isSequential;

			return isSequential && before.isParentFolder(newParent);
		}

		if (newParent != null) {
			return Objects.requireNonNullElse(before, after).isParentFolder(newParent);
		}
		return true;
	}

	/** 같은 폴더로 이동하는지 확인 */
	protected boolean moveToSameFolder(T entity, T before, T after, Dnd newParent) {
		if (newParent != null) {
			return entity.isParentFolder(newParent);
		}
		T targetFile = (before != null) ? before : after;
		return entity.isInSameParent(targetFile);
	}

	/** 다른 폴더에서 위치 바꾸기*/
	private void updatePositionInDifferentFolder(T entity, long newPosition, DndFolder oldFolder, DndFolder newFolder) {
		shiftFolderUp(entity.getPosition(), oldFolder.getId());
		shiftFolderDown(newPosition, newFolder.getId());
		entity.updateFolder(newFolder);
	}

	/** 다른 폴더 - 옮긴 후 바뀐 위치의 밑에 있는 파일들을 아래로 내린다.(+1) */
	protected void shiftFolderDown(long targetPosition, Long folderId) {
		dndAdapter().incrementPositionAfter(targetPosition, folderId);
	}
}
