package com.owing.api.dnd.base.service;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;

public interface ReadDndUseCase {
	DndInfoResponse executeRetrieve(Long dndId);
}