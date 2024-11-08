package com.owing.entity.dnd.base.orderStrategy;

import com.owing.entity.dnd.base.model.DndEntity;

public interface OrderingStrategy<T extends DndEntity> {
	long getNewEntityPosition(Long parentId);
	void reorderEntity(T dndEntity);
	T updatePosition(T dndEntity, long newPosition);
}
