package com.owing.entity.dnd.file.service;

import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.entity.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.file.repository.BaseFileRepository;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

public abstract class BaseFileDomainService<T extends BaseFileEntity<F>, F extends BaseFolderEntity> extends DndDomainService<T> {

	public BaseFileDomainService(BaseFileRepository<T, F> dndRepository,
		BaseFileAdapter<T> dndAdapter,
		OrderingStrategy<T> orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}

	public T updateEntityPosition(T entity, Long newPosition, Long newParentId) {
		return orderingStrategy.updatePosition(entity, newPosition);
	}
}
