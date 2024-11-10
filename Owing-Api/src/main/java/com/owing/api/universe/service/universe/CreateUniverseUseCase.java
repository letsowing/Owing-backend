package com.owing.api.universe.service.universe;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.universe.adaptor.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUniverseUseCase {
	private final UniverseDomainService universeDomainService;
	private final UniverseFolderAdapter universeFolderAdaptor;
	private final UniverseMapper universeMapper;

	@Transactional
	public UniverseShortInfoResponse execute(AddUniverseRequest addUniverseRequest) {

		// UniverseFolder universeFolder = null; // todo: 폴더 생긴 후 수정
		UniverseFolder universeFolder = universeFolderAdaptor.findById(addUniverseRequest.folderId());

		Universe universe = universeMapper.toEntity(addUniverseRequest, universeFolder);
		Universe savedUniverse = universeDomainService.createEntity(universe);
		return universeMapper.toShortInfoResponse(savedUniverse);
	}
}
