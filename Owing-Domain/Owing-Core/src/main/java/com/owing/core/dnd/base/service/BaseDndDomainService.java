package com.owing.core.dnd.base.service;

import java.util.List;

import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;

public abstract class BaseDndDomainService<T extends BaseDnd>{
	protected abstract BaseDndRepository<T> dndRepository();
	protected abstract BaseDndAdapter<T> dndEntityAdapter();
	protected abstract OrderingStrategy<T> orderingStrategy();

	public T getEntity(Long id) {
		return dndEntityAdapter().findById(id);
	}

	public T getOptionalEntity(Long id){
		if(id == null){
			return null;
		}
		return dndEntityAdapter().findById(id);
	}

	public List<T> getEntityList(Long parentId) {
		return dndEntityAdapter().findAllByParentId(parentId);
	}

	public T createEntity(T entity) {
		long position = orderingStrategy().getNewPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndRepository().save(entity);
	}

	public abstract T updateTitle(T entity, T newEntity);

	public void deleteEntity(T entity) {
		orderingStrategy().reorderEntity(entity);
		dndRepository().delete(entity);
	}

	public T updateEntityPosition(T entity, T beforeEntity, T afterEntity) {
		return orderingStrategy().updatePosition(entity, beforeEntity, afterEntity);
	}
	public T updateEntityPosition(T entity, T beforeEntity, T afterEntity, BaseDnd newFolder) {
		return orderingStrategy().updatePosition(entity, beforeEntity, afterEntity, newFolder);
	}

}
