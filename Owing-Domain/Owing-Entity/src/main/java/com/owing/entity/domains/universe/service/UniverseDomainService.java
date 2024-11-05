package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniverseDomainService {

	private final UniverseAdaptor universeAdaptor;
	private final UniverseRepository universeRepository;

	@Transactional
	public Universe createUniverse(Universe universe) {
		Universe savedUniverse = universeRepository.save(universe);
		return savedUniverse;
	}



}
