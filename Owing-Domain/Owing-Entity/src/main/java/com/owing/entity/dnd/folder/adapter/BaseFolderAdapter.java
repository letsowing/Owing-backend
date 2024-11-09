package com.owing.entity.dnd.folder.adapter;

import com.owing.entity.dnd.base.adapter.BaseDndAdapter;
import com.owing.entity.dnd.base.repository.BaseDndRepository;
import com.owing.entity.dnd.folder.model.BaseFolder;

public abstract class BaseFolderAdapter<T extends BaseFolder> extends BaseDndAdapter<T> {
	public BaseFolderAdapter(BaseDndRepository<T> baseDndRepository) {
		super(baseDndRepository);
	}
}
