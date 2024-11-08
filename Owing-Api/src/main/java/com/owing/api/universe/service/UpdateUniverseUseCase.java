package com.owing.api.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
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
