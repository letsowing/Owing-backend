package com.owing.core.dnd.folder.adapter;

import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFolderAdapter<T extends BaseFolder> extends BaseDndAdapter<T> {
	public BaseFolderAdapter(BaseDndRepository<T> baseDndRepository) {
		super(baseDndRepository);
	}
}
