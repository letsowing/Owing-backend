package com.owing.core.dnd.base.adapter;

import java.util.List;

import com.owing.core.dnd.error.DndErrorCode;
import com.owing.core.dnd.error.exception.DndNotFoundException;
import com.owing.core.dnd.base.model.Dnd;
import com.owing.core.dnd.base.repository.DndRepository;

public abstract class DndAdapter<T extends Dnd> {
	protected abstract DndRepository<T> dndRepository();

	public T findById(Long id) {
		return dndRepository().findById(id).orElseThrow(() -> DndNotFoundException.of(DndErrorCode.DND_NOT_FOUND));
	}

	public List<T> findAllByParentId(Long parentId){
		return dndRepository().findByParentId(parentId);
	}

	public long getMaxPositionByParentId(Long parentId){
		return dndRepository().getMaxPositionByParentId(parentId);
	}

	public void decrementPositionAfter(long position, Long parentId) {
		dndRepository().decrementPositionAfter(position, parentId);
	}

	public void decrementPositionBetween(long fromPosition, long toPosition, Long parentId) {
		dndRepository().decrementPositionBetween(fromPosition, toPosition, parentId);
	}

	public void incrementPositionBetween(long fromPosition, long toPosition, Long parentId) {
		dndRepository().incrementPositionBetween(fromPosition, toPosition, parentId);
	}

	public T save(T entity) {
		return dndRepository().save(entity);
	}

	public T updatePosition(T entity) {
		return save(entity);
	}

	public T updateName(T entity) {
        return save(entity);
	}

	public void delete(T entity) {
        dndRepository().delete(entity);
    }
}
