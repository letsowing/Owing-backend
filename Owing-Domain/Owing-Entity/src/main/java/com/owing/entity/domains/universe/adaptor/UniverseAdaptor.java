package com.owing.entity.domains.universe.adaptor;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.universe.error.UniverseErrorCode;
import com.owing.entity.domains.universe.error.exception.UniverseNotFoundException;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseAdaptor {
	private final UniverseRepository universeRepository;

	public Universe findById(Long universeId) {
		return universeRepository.findById(universeId)
			.orElseThrow(() -> UniverseNotFoundException.of(UniverseErrorCode.UNIVERSE_NOT_FOUND));
	}
}
