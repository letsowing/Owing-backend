package com.owing.core.dnd.orderStrategy.lexorank;

import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.DndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankOrderingStrategy<T extends Dnd> implements OrderingStrategy<T> {
	protected final DndAdapter<T> dndEntityAdapter;
	protected final DndRepository<T> dndEntityRepository;

	@Override
	public long getNewPosition(Long parentId) {
		return 0;
	}

	@Override
	public void reorderEntity(T dndEntity) {

	}

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity) {
		return null;
	}

	@Override
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, Dnd newParent) {
		return null;
	}

	@Override
	public long getUpdatePosition(T entity, T beforeEntity, T afterEntity) {
		return 0;
	}

}
