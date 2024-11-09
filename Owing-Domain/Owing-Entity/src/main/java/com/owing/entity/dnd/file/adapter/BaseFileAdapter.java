package com.owing.entity.dnd.file.adapter;

import com.owing.entity.dnd.base.adapter.BaseDndAdapter;
import com.owing.entity.dnd.base.model.BaseDnd;
import com.owing.entity.dnd.base.repository.BaseDndRepository;

public abstract class BaseFileAdapter<T extends BaseDnd> extends BaseDndAdapter<T> {
	public BaseFileAdapter(BaseDndRepository<T> dndRepository) {
		super(dndRepository);
	}
}
