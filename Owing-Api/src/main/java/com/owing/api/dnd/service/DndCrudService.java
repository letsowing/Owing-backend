package com.owing.api.dnd.service;

import com.owing.api.dnd.model.dto.response.DndInfoResponse;

public interface DndCrudService<A, U, P> {
	DndInfoResponse create(A a);
	DndInfoResponse get(Long dndId);
	void updateName(Long id, U dto);
	void updatePosition(Long id, P dto);
	void delete(Long dndId) ;
}
