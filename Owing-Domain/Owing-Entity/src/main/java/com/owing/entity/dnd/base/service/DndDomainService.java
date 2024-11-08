package com.owing.entity.dnd.base.service;

import java.util.List;

import com.owing.entity.dnd.base.adapter.DndAdapter;
import com.owing.entity.dnd.base.model.DndEntity;
import com.owing.entity.dnd.base.repository.DndRepository;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DndDomainService<T extends DndEntity>{
	protected final DndRepository<T> dndRepository;
	protected final DndAdapter<T> dndAdapter;
	protected final OrderingStrategy<T> orderingStrategy;

	public T getEntity(Long id) {
		return dndAdapter.findById(id);
	}

	public List<T> getEntityList(Long parentId) {
		return dndAdapter.findAllByParentId(parentId);
	}

	public T createEntity(T entity) {
		System.out.println(entity.toString());
		long position = orderingStrategy.getNewEntityPosition(entity.getParentId());
		entity.updatePosition(position);
		return dndRepository.save(entity);
	}

	public T updateEntity(T entity, T newEntity) {
		entity.update(newEntity);
		return dndRepository.save(entity);
	}

	public void deleteEntity(T entity) {
		orderingStrategy.reorderEntity(entity);
		dndRepository.delete(entity);
	}

	public T updateEntityPosition(T entity, Long newPosition) {
		return orderingStrategy.updatePosition(entity, newPosition);
	}

}
