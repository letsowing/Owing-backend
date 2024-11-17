package com.owing.api.dnd.base.service;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;

public interface CreateDndUseCase<A>{
    DndInfoResponse execute(A a);
}