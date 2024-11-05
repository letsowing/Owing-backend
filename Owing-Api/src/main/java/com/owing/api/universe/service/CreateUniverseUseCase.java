package com.owing.api.universe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.service.UniverseDomainService;
import com.owing.entity.folders.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUniverseUseCase {

	private final UniverseDomainService universeDomainService;
	// private final UniverseFolderAdaptor universeFolderAdaptor;
	private final UniverseMapper universeMapper;

	@Transactional
	public UniverseShortInfoResponse execute(AddUniverseRequest addUniverseRequest) {

		UniverseFolder universeFolder = null; // todo: 폴더 생긴 후 수정
		// UniverseFolder universeFolder = universeFolderAdaptor.findById(addUniverseRequest.folderId());

		Universe universe = universeMapper.toEntity(addUniverseRequest, universeFolder);
		Universe savedUniverse = universeDomainService.createUniverse(universe);
		return universeMapper.toShortInfoResponse(savedUniverse);
	}
}
