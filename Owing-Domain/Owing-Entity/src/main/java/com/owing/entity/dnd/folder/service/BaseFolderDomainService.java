package com.owing.entity.dnd.folder.service;

import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.entity.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;
import com.owing.entity.dnd.folder.repository.BaseFolderRepository;

public abstract class BaseFolderDomainService<T extends BaseFolderEntity> extends DndDomainService<T> {
	public BaseFolderDomainService(BaseFolderRepository<T> dndRepository,
		BaseFolderAdapter<T> dndAdapter,
		OrderingStrategy<T> orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}
}
