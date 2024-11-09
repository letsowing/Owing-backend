package com.owing.entity.dnd.base.orderStrategy.lexorank;

import com.owing.entity.dnd.base.adapter.BaseDndAdapter;
import com.owing.entity.dnd.base.model.BaseDnd;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.entity.dnd.base.repository.BaseDndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankOrderingStrategy<T extends BaseDnd> implements OrderingStrategy<T> {
	protected final BaseDndAdapter<T> dndEntityAdapter;
	protected final BaseDndRepository<T> dndEntityRepository;

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
