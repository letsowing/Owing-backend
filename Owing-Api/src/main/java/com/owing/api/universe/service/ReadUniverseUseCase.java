package com.owing.api.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadUniverseUseCase {

	private final UniverseAdaptor universeAdaptor;
	private final UniverseMapper universeMapper;

	@Transactional
	public UniverseShortInfoResponse execute(Long universeId) {

		Universe universe = universeAdaptor.findById(universeId);
		return universeMapper.toShortInfoResponse(universe);
	}
}
