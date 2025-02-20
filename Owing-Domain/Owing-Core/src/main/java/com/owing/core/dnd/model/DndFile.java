package com.owing.core.dnd.model;

public interface DndFile extends Dnd {
	<T extends DndFolder> T getFolder();
	void updateFolder(DndFolder folder);
	boolean isChildOf(DndFolder folder);
}
