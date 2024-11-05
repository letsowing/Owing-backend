package com.owing.api.universe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.service.UniverseDomainService;
import com.owing.entity.folders.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUniverseUseCase {

	private final UniverseDomainService universeDomainService;
	private final UniverseAdaptor universeAdaptor;
	private final UniverseMapper universeMapper;

	@Transactional
	public UniverseShortInfoResponse execute(Long universeId, UpdateUniverseRequest updateUniverseRequest) {

		Universe oldUniverse = universeAdaptor.findById(universeId);
		Universe newUniverse = universeMapper.toEntity(oldUniverse, updateUniverseRequest);
		Universe updatedUniverse = universeDomainService.updateUniverse(oldUniverse, newUniverse);
		return universeMapper.toShortInfoResponse(updatedUniverse);
	}
}
