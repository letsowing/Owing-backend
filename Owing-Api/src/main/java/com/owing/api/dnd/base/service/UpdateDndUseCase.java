package com.owing.api.dnd.base.service;

public interface UpdateDndUseCase<U, P> {
    void executeUpdateTitle(Long id, U dto);
    void executeUpdatePosition(Long id, P dto);
}
