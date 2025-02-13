package com.owing.core.dnd.service.lexorank;

import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.service.DndService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankDndService<T extends Dnd> implements DndService<T> {
	protected final DndAdapter<T> dndAdapter;

	@Override
	public long getNewPosition(Long parentId) {
		return 0;
	}

	@Override
	public void reorderEntity(T dndEntity) {

	}

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, Dnd newParent) {
		return null;
	}

	@Override
	public long getUpdatePosition(T entity, T beforeEntity, T afterEntity, Dnd newParent) {
		return 0;
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
