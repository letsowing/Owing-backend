package com.owing.api.dnd.base.model.mapper;

import java.util.List;

import com.owing.api.dnd.base.model.dto.request.AddDndRequest;
import com.owing.api.dnd.base.model.dto.request.UpdateDndRequest;
import com.owing.api.dnd.base.model.dto.response.DndInfoListResponse;
import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.core.dnd.base.model.BaseDnd;

@Mapper
public interface DndMapper<T extends BaseDnd, R extends AddDndRequest, U extends UpdateDndRequest> {
    T toEntity(R addDndRequest);
    T toEntity(U updateDndRequest);
    DndInfoResponse toInfoResponse(T entity);
    DndInfoListResponse toListResponse(List<T> dndList);
}
