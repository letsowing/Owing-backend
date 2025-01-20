package com.owing.core.dnd.base.service;

import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;

public abstract class DndService<T extends Dnd>{
	protected abstract DndAdapter<T> dndAdapter();
	protected abstract OrderingStrategy<T> orderingStrategy();

	public T create(T entity) {
		long position = orderingStrategy().getNewPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndAdapter().save(entity);
	}

	public T update(T entity, T newEntity) {
		entity.updateName(newEntity.getName());
		return dndAdapter().updateName(entity);
	}

	public void delete(T entity) {
		orderingStrategy().reorderEntity(entity);
		dndAdapter().delete(entity);
	}

	public T updatePosition(T entity, T beforeEntity, T afterEntity, Dnd newFolder) {
		return orderingStrategy().updatePosition(entity, beforeEntity, afterEntity, newFolder);
	}

}
