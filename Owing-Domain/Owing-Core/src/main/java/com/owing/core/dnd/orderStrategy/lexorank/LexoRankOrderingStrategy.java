package com.owing.core.dnd.orderStrategy.lexorank;

import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankOrderingStrategy<T extends Dnd> implements OrderingStrategy<T> {
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

}
