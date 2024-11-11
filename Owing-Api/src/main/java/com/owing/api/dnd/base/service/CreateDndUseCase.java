package com.owing.api.dnd.base.service;

public interface CreateDndUseCase<R, A>{
    R execute(A a);
}