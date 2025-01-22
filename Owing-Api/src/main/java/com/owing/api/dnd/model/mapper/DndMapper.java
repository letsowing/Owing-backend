package com.owing.api.dnd.model.mapper;

import com.owing.api.dnd.model.dto.response.DndInfoResponse;
import com.owing.core.dnd.base.model.Dnd;

public interface DndMapper<T extends Dnd> {
    DndInfoResponse toInfoResponse(T entity);
}
