package com.owing.core.dnd.model;

import java.util.List;
public interface DndFolder extends Dnd {
	List<? extends DndFile> getFiles();
	Long getProjectId();
}
