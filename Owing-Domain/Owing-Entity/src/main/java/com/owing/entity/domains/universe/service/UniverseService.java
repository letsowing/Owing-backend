package com.owing.entity.domains.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniverseService extends DndService<Universe> {

	private final UniverseRepository universeRepository;
	private final UniverseAdapter universeAdapter;
	private final UniverseShiftOrderingStrategy orderingStrategy;

	@Transactional
	public Universe create(Universe universe) {
		Universe savedUniverse = universeRepository.save(universe);
		return savedUniverse;
	}

	@Transactional
	public Universe update(Universe oldUniverse, Universe newUniverse) {
		Universe updatedUniverse = oldUniverse.updateUniverse(newUniverse);
		return updatedUniverse;
	}


	@Override
	protected DndAdapter<Universe> dndAdapter() {
		return universeAdapter;
	}

	@Override
	protected OrderingStrategy<Universe> orderingStrategy() {
		return orderingStrategy;
	}

	@Transactional
	public void restoreById(Long itemId) {
		universeRepository.restoreById(itemId);
	}
}
