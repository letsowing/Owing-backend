package com.owing.core.dnd.file.model;

import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.folder.model.BaseFolder;

public interface BaseFile<T extends BaseFolder> extends BaseDnd {
	String getName();
	void updateFolder(T folder);
	T getFolder();
	void updateName(String newTitle);
}
