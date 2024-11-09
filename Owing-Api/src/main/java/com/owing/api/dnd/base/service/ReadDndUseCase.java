package com.owing.api.dnd.base.service;

public interface ReadDndUseCase<R, L> {
	R executeRetrieve(Long dndId);
	L executeList(Long dndId);
}