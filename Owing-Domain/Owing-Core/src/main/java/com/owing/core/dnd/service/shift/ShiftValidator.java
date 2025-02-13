package com.owing.core.dnd.service.shift;

import java.util.Objects;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;

public class ShiftValidator {

	/** 붙어있는지 */
	public static boolean isSequentialPosition(Dnd before, Dnd after) {
		return after.getPosition() - before.getPosition() == 1;
	}

	public static boolean validatePosition(Dnd entity, Dnd before, Dnd after, DndFolder newParent) {
		if (entity instanceof DndFolder) {
			if (before == null && after == null) return false;
			if (before != null && after != null) return isSequentialPosition(before, after);
			return entity.isInSameParent(Objects.requireNonNullElse(before, after));
		}

		if (before == null && after == null) return newParent != null;

		if (before != null && after != null) {
			boolean isSequential = isSequentialPosition(before, after) && before.isInSameParent(after); // 둘다 있으면
			return newParent == null ? isSequential : isSequential && ((DndFile)before).isChildOf(newParent);
		}

		return newParent == null || ((DndFile)Objects.requireNonNullElse(before, after)).isChildOf(newParent);
	}

	/** 같은 폴더로 이동하는지 확인 */
	public static boolean isMoveInSameFolder(Dnd entity, Dnd before, Dnd after, DndFolder newParent) {
		if (newParent != null) {
			return ((DndFile)entity).isChildOf(newParent);
		}
		Dnd targetFile = (before != null) ? before : after;
		return entity.isInSameParent(targetFile);
	}

}
