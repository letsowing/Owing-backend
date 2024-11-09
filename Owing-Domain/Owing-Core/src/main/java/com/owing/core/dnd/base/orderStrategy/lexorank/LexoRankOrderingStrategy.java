package com.owing.core.dnd.base.orderStrategy.lexorank;

import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LexoRankOrderingStrategy<T extends BaseDnd> implements OrderingStrategy<T> {
	protected final BaseDndAdapter<T> dndEntityAdapter;
	protected final BaseDndRepository<T> dndEntityRepository;

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
	public T updatePosition(T dndEntity, T beforeEntity, T afterEntity, BaseDnd newParent) {
		return null;
	}

	@Override
	public long getUpdatePosition(T entity, T beforeEntity, T afterEntity) {
		return 0;
	}

}
