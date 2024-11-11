package com.owing.core.dnd.folder.service;

import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFolderDomainService<T extends BaseFolder> extends BaseDndDomainService<T> {

	public T updateTitle(T entity, T newEntity) {
		entity.updateTitle(newEntity.getName());
		dndRepository().save(entity);
		return entity;
	}
}
