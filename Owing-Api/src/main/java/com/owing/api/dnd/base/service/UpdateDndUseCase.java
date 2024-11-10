package com.owing.api.dnd.base.service;

public interface UpdateDndUseCase<R, U, P> {
    R executeUpdateTitle(Long id, U dto);
    R executeUpdatePosition(Long id, P dto);
}
