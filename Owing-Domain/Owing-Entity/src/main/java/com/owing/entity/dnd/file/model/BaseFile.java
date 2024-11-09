package com.owing.entity.dnd.file.model;

import com.owing.entity.dnd.base.model.BaseDnd;
import com.owing.entity.dnd.folder.model.BaseFolder;

public interface BaseFile<T extends BaseFolder> extends BaseDnd {
	String getTitle();
	void updateFolder(T folder);
	T getFolder();
}
