package com.owing.core.dnd.base.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.owing.core.dnd.base.model.Dnd;

public interface DndRepository<T extends Dnd> extends CrudRepository<T, Long> {
	List<T> findByParentId(Long parentId);
	void restoreById(Long id);
}
