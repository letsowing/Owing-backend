package com.owing.entity.dnd.file.service;

import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.entity.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.file.repository.BaseFileRepository;

public abstract class BaseFileDomainService<T extends BaseFileEntity> extends DndDomainService<T> {

	public BaseFileDomainService(BaseFileRepository<T> dndRepository,
		BaseFileAdapter<T> dndAdapter,
		OrderingStrategy<T> orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}

	public T updateEntityPosition(T entity, Long newPosition, Long newParentId) {
		return orderingStrategy.updatePosition(entity, newPosition);
	}
}
