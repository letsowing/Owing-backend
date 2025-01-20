package com.owing.api.universe.service.universe;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadUniverseUseCase extends ReadFileUseCase<Universe, UniverseFolder> {

	private final UniverseMapper universeMapper;
	private final UniverseAdapter universeAdapter;

	@Override
	protected BaseFileMapper<Universe, UniverseFolder> fileMapper() {
		return universeMapper;
	}

	@Override
	protected DndAdapter<Universe> fileAdapter() {
		return universeAdapter;
	}
}
