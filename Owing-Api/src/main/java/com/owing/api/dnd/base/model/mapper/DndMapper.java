package com.owing.api.dnd.base.model.mapper;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.core.dnd.base.model.BaseDnd;

public interface DndMapper<T extends BaseDnd> {
    DndInfoResponse toInfoResponse(T entity);
}
