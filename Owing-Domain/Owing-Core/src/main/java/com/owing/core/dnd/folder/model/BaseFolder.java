package com.owing.core.dnd.folder.model;

import com.owing.core.dnd.base.model.BaseDnd;

public interface BaseFolder extends BaseDnd{
	String getName();
	Long getProjectId();
	void updateTitle(String newTitle);
}
