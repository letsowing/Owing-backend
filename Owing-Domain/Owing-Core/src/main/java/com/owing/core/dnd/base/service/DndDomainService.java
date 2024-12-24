package com.owing.core.dnd.base.service;

import java.util.List;

import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;

public abstract class DndDomainService<T extends Dnd>{
	protected abstract DndAdapter<T> dndAdapter();
	protected abstract OrderingStrategy<T> orderingStrategy();

	public T getEntity(Long id) {
		return dndAdapter().findById(id);
	}

	public T getOptionalEntity(Long id){
		if(id == null){
			return null;
		}
		return this.getEntity(id);
	}

	public List<T> getEntityList(Long parentId) {
		return dndAdapter().findAllByParentId(parentId);
	}

	public T createEntity(T entity) {
		long position = orderingStrategy().getNewPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndAdapter().save(entity);
	}

	public T updateName(T entity, T newEntity) {
		entity.updateName(newEntity.getName());
		return dndAdapter().updateName(entity);
	}

	public void deleteEntity(T entity) {
		orderingStrategy().reorderEntity(entity);
		dndAdapter().delete(entity);
	}

	public T updateEntityPosition(T entity, T beforeEntity, T afterEntity) {
		return orderingStrategy().updatePosition(entity, beforeEntity, afterEntity);
	}
	public T updateEntityPosition(T entity, T beforeEntity, T afterEntity, Dnd newFolder) {
		return orderingStrategy().updatePosition(entity, beforeEntity, afterEntity, newFolder);
	}

}
