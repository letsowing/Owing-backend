package com.owing.entity.domains.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;

import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniverseDomainService {

	private final UniverseRepository universeRepository;

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

	@Transactional
	public void deleteUniverse(Universe universe) {
		universeRepository.delete(universe);
	}
}
