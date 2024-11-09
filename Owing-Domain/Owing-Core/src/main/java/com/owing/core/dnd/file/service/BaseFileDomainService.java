package com.owing.core.dnd.file.service;

import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.file.repository.BaseFileRepository;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFileDomainService<T extends BaseFile<F>, F extends BaseFolder> extends
	BaseDndDomainService<T> {

	public BaseFileDomainService(BaseFileRepository<T, F> dndRepository,
		BaseFileAdapter<T> dndAdapter,
		OrderingStrategy<T> orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}

	public T updateEntityPosition(T entity, Long newPosition, Long newParentId) {
		return orderingStrategy.updatePosition(entity, newPosition);
	}

	public T updateTitle(T entity, T newEntity) {
		entity.updateTitle(newEntity.getTitle());
		dndRepository.save(entity);
		return entity;
	}
}
