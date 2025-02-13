package com.owing.core.dnd.service.lexorank;

import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.service.DndService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankDndService<T extends Dnd> implements DndService<T> {
	protected final DndAdapter<T> dndAdapter;

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, DndFolder newParent) {
		return null;
	}

	@Override
	public T create(T entity) {
		return null;
	}

	@Override
	public T delete(T entity) {
		return null;
	}

}
