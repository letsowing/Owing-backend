package com.owing.entity.dnd.folder.adapter;

import com.owing.entity.dnd.base.adapter.DndAdapter;
import com.owing.entity.dnd.base.repository.DndRepository;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

public abstract class BaseFolderAdapter<T extends BaseFolderEntity> extends DndAdapter<T> {
	public BaseFolderAdapter(DndRepository<T> dndRepository) {
		super(dndRepository);
	}
}
