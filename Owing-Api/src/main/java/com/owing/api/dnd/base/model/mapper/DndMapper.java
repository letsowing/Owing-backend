package com.owing.api.dnd.base.model.mapper;

import java.util.List;

import com.owing.api.dnd.base.model.dto.request.AddDndRequest;
import com.owing.api.dnd.base.model.dto.request.UpdateDndRequest;
import com.owing.api.dnd.base.model.dto.response.DndInfoListResponse;
import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.core.dnd.base.model.BaseDnd;

public interface DndMapper<T extends BaseDnd, A extends AddDndRequest, U extends UpdateDndRequest> {
    T toEntity(A addDndRequest);
    T toEntity(U updateDndRequest);
    DndInfoResponse toInfoResponse(T entity);
    DndInfoListResponse toListResponse(List<T> dndList);
}
