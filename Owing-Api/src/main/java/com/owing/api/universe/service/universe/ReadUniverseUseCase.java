package com.owing.api.universe.service.universe;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadUniverseUseCase extends ReadFileUseCase<Universe, UniverseFolder> {

	private final UniverseMapper universeMapper;
	private final UniverseDomainService baseDndDomainService;
	private final UniverseAdapter universeAdapter;

	@Override
	protected BaseFileMapper<Universe, UniverseFolder> dndMapper() {
		return universeMapper;
	}

	@Override
	protected BaseDndDomainService<Universe> baseDndDomainService() {
		return baseDndDomainService;
	}
}
