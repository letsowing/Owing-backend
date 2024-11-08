package com.owing.entity.dnd.file.adapter;

import com.owing.entity.dnd.base.adapter.DndAdapter;
import com.owing.entity.dnd.base.repository.DndRepository;
import com.owing.entity.dnd.file.model.BaseFileEntity;

public abstract class BaseFileAdapter<T extends BaseFileEntity> extends DndAdapter<T> {
	public BaseFileAdapter(DndRepository<T> dndRepository) {
		super(dndRepository);
	}
}
