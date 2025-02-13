package com.owing.core.dnd.service;

import com.owing.core.dnd.model.Dnd;

public interface DndService<T extends Dnd> {
	long getNewPosition(Long parentId);
	long getUpdatePosition(T entity, T before, T after, Dnd newParent);
	void reorderEntity(T entity);

	T create(T entity);
	T updatePosition(T entity, T before, T after, Dnd newParent);
	T delete(T entity);
}
