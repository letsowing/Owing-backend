package com.owing.core.dnd.base.orderStrategy;

import com.owing.core.dnd.base.model.BaseDnd;

public interface OrderingStrategy<T extends BaseDnd> {
	long getNewPosition(Long parentId);
	void reorderEntity(T dndEntity);
	T updatePosition(T dndEntity, T beforeEntity, T afterEntity);
	T updatePosition(T dndEntity, T beforeEntity, T afterEntity, BaseDnd newParent);
	long getUpdatePosition(T entity, T beforeEntity, T afterEntity);
}
