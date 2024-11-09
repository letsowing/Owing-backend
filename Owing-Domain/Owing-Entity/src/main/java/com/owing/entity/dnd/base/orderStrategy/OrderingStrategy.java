package com.owing.entity.dnd.base.orderStrategy;

import com.owing.entity.dnd.base.model.BaseDnd;

public interface OrderingStrategy<T extends BaseDnd> {
	long getNewEntityPosition(Long parentId);
	void reorderEntity(T dndEntity);
	T updatePosition(T dndEntity, long newPosition);
}
