package com.owing.core.dnd.orderStrategy;

import com.owing.core.dnd.base.model.Dnd;

public interface OrderingStrategy<T extends Dnd> {
	long getNewPosition(Long parentId);
	void reorderEntity(T entity);
	T updatePosition(T entity, T before, T after, Dnd newParent);
	long getUpdatePosition(T entity, T before, T after, Dnd newParent);
}
