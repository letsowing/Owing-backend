package com.owing.entity.dnd.base.orderStrategy.lexorank;

import com.owing.entity.dnd.base.adapter.DndAdapter;
import com.owing.entity.dnd.base.model.DndEntity;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.entity.dnd.base.repository.DndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankOrderingStrategy<T extends DndEntity> implements OrderingStrategy<T> {
	protected final DndAdapter<T> dndAdapter;
	protected final DndRepository<T> dndRepository;

	@Override
	public long getNewEntityPosition(Long parentId) {
		return 0;
	}

	@Override
	public void reorderEntity(T dndEntity) {

	}

	@Override
	public T updatePosition(T dndEntity, long newPosition) {
		return null;
	}
}
