package com.owing.entity.domains.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;

import lombok.RequiredArgsConstructor;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniverseService extends DndService<Universe> {

	private final UniverseAdapter universeAdapter;
	private final UniverseShiftOrderingStrategy orderingStrategy;

	@Override
	protected DndAdapter<Universe> dndAdapter() {
		return universeAdapter;
	}

	@Override
	protected OrderingStrategy<Universe> orderingStrategy() {
		return orderingStrategy;
	}

}
