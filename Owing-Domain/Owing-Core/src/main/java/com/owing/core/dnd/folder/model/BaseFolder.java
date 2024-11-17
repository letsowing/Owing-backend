package com.owing.core.dnd.folder.model;

import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.file.model.BaseFile;

import java.util.List;

public interface BaseFolder extends BaseDnd{
	String getName();
	Long getProjectId();
	List<? extends BaseFile> getFiles();
	void updateTitle(String newTitle);
}
