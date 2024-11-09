package com.owing.core.dnd.folder.service;

import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.core.dnd.folder.repository.BaseFolderRepository;

public abstract class BaseFolderDomainService<T extends BaseFolder> extends BaseDndDomainService<T> {
	public BaseFolderDomainService(BaseFolderRepository<T> dndRepository,
		BaseFolderAdapter<T> dndAdapter,
		OrderingStrategy<T> orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}

	public T updateTitle(T entity, T newEntity) {
		entity.updateTitle(newEntity.getName());
		dndRepository.save(entity);
		return entity;
	}
}
