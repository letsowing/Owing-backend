package com.owing.core.dnd.base.service;

import java.util.List;

import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseDndDomainService<T extends BaseDnd>{
	protected final BaseDndRepository<T> dndRepository;
	protected final BaseDndAdapter<T> dndEntityAdapter;
	protected final OrderingStrategy<T> orderingStrategy;

	public T getEntity(Long id) {
		return dndEntityAdapter.findById(id);
	}

	public List<T> getEntityList(Long parentId) {
		return dndEntityAdapter.findAllByParentId(parentId);
	}

	public T createEntity(T entity) {
		long position = orderingStrategy.getNewEntityPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndRepository.save(entity);
	}

	public abstract T updateTitle(T entity, T newEntity);

	public void deleteEntity(T entity) {
		orderingStrategy.reorderEntity(entity);
		dndRepository.delete(entity);
	}

	public T updateEntityPosition(T entity, Long newPosition) {
		return orderingStrategy.updatePosition(entity, newPosition);
	}

}
