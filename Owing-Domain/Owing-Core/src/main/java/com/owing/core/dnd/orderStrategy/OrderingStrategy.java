package com.owing.core.dnd.orderStrategy;

import com.owing.core.dnd.base.model.Dnd;

public interface OrderingStrategy<T extends Dnd> {
	long getNewPosition(Long parentId);
	void reorderEntity(T dndEntity);
	T updatePosition(T dndEntity, T beforeEntity, T afterEntity);
	T updatePosition(T dndEntity, T beforeEntity, T afterEntity, Dnd newParent);
	long getUpdatePosition(T entity, T beforeEntity, T afterEntity);
}
