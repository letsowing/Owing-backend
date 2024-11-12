package com.owing.core.dnd.file.service;

import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFileDomainService<T extends BaseFile<F>, F extends BaseFolder> extends
	BaseDndDomainService<T> {

	public T updateEntityPosition(T entity, T beforeEntity, T afterEntity, F newParent) {
		return orderingStrategy().updatePosition(entity, beforeEntity, afterEntity, newParent);
	}

	public T updateName(T entity, T newEntity) {
		entity.updateName(newEntity.getName());
		dndRepository().save(entity);
		return entity;
	}
}
