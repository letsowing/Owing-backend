package com.owing.entity.domains.universe.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.domains.universe.error.UniverseErrorCode;
import com.owing.entity.domains.universe.error.exception.UniverseNotFoundException;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.repository.UniverseRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseAdapter extends BaseFileAdapter<Universe> {
	private final UniverseRepository universeRepository;

	@Override
	protected BaseDndRepository<Universe> dndRepository() {
		return universeRepository;
	}

	public Universe findById(Long universeId) {
		return universeRepository.findById(universeId)
			.orElseThrow(() -> UniverseNotFoundException.of(UniverseErrorCode.UNIVERSE_NOT_FOUND));
	}
}
