package com.owing.core.dnd.service;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.model.DndFolder;

public interface DndService<T extends Dnd> {
	T create(T entity);
	T delete(T entity);
	T updatePosition(T entity, T before, T after, DndFolder parent);
}
