package com.owing.entity.dnd.base.adapter;

import java.util.List;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.dnd.base.error.DndErrorCode;
import com.owing.entity.dnd.base.error.exception.DndNotFoundException;
import com.owing.entity.dnd.base.model.DndEntity;
import com.owing.entity.dnd.base.repository.DndRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public abstract class DndAdapter<T extends DndEntity> {
	protected final DndRepository<T> dndRepository;

	public T findById(Long id) {
		return dndRepository.findById(id).orElseThrow(() -> DndNotFoundException.of(DndErrorCode.DND_NOT_FOUND));
	}

	public List<T> findAllByParentId(Long parentId){
		return dndRepository.findByParentId(parentId);
	}

	public long getMaxPositionByParentId(Long parentId){
		return dndRepository.getMaxPositionByParentId(parentId);
	}

	public void decrementPositionAfter(long position, Long parentId) {
		dndRepository.decrementPositionAfter(position, parentId);
	}

	public void decrementPositionBetween(long fromPosition, long toPosition, Long parentId) {
		dndRepository.decrementPositionBetween(fromPosition, toPosition, parentId);
	}

	public void incrementPositionBetween(long fromPosition, long toPosition, Long parentId) {
		dndRepository.incrementPositionBetween(fromPosition, toPosition, parentId);
	}
}
