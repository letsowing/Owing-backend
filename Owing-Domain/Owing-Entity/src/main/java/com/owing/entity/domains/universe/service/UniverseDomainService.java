package com.owing.entity.domains.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.service.BaseFileDomainService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniverseDomainService extends BaseFileDomainService<Universe, UniverseFolder> {

	private final UniverseRepository universeRepository;
	private final UniverseAdapter universeAdapter;
	private final UniverseShiftOrderingStrategy orderingStrategy;

	@Transactional
	public Universe createUniverse(Universe universe) {
		Universe savedUniverse = universeRepository.save(universe);
		return savedUniverse;
	}

	@Transactional
	public Universe updateUniverse(Universe oldUniverse, Universe newUniverse) {
		Universe updatedUniverse = oldUniverse.updateUniverse(newUniverse);
		return updatedUniverse;
	}

	@Override
	protected BaseDndRepository<Universe> dndRepository() {
		return universeRepository;
	}

	@Override
	protected BaseDndAdapter<Universe> dndEntityAdapter() {
		return universeAdapter;
	}

	@Override
	protected OrderingStrategy<Universe> orderingStrategy() {
		return orderingStrategy;
	}

	public void restoreById(Long itemId) {
		universeRepository.restoreById(itemId);
	}
}
