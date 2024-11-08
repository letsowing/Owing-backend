package com.owing.api.dnd.base.service;

public interface UpdateDndUseCase<R, U, P> {
    R execute(Long id, U dto);
    R executeUpdatePosition(Long id, P dto);
}
