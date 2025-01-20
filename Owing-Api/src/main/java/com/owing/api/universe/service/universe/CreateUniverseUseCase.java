package com.owing.api.universe.service.universe;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUniverseUseCase extends CreateFileUseCase<Universe, UniverseFolder> {
	private final MemberUtils memberUtils;
	private final UniverseService universeService;
	private final UniverseFolderAdapter universeFolderAdaptor;
	private final UniverseMapper universeMapper;

	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse execute(AddUniverseRequest addUniverseRequest) {
		UniverseFolder universeFolder = universeFolderAdaptor.findById(addUniverseRequest.folderId());
		Universe universe = universeMapper.toEntity(addUniverseRequest, universeFolder);
		Universe savedUniverse = universeService.create(universe);
		return universeMapper.toInfoResponse(savedUniverse);
	}

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndService<Universe> fileService() {
		return universeService;
	}

	@Override
	protected BaseFileMapper<Universe, UniverseFolder> fileMapper() {
		return universeMapper;
	}

	@Override
	protected DndAdapter<UniverseFolder> folderAdapter() {
		return universeFolderAdaptor;
	}
}
