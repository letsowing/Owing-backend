package com.owing.entity.dnd.folder.model;

import com.owing.entity.dnd.base.model.BaseDnd;

public interface BaseFolder extends BaseDnd{
	String getName();
	Long getProjectId();
	void updateTitle(String newTitle);
}
