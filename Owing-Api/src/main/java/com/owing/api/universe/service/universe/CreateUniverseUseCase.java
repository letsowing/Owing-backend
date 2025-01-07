package com.owing.api.universe.service.universe;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUniverseUseCase extends CreateFileUseCase<Universe, UniverseFolder> {
	private final MemberUtils memberUtils;
	private final UniverseDomainService universeDomainService;
	private final UniverseFolderAdapter universeFolderAdaptor;
	private final UniverseMapper universeMapper;

	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse execute(AddUniverseRequest addUniverseRequest) {
		UniverseFolder universeFolder = universeFolderAdaptor.findById(addUniverseRequest.folderId());
		Universe universe = universeMapper.toEntity(addUniverseRequest, universeFolder);
		Universe savedUniverse = universeDomainService.createEntity(universe);
		return universeMapper.toInfoResponse(savedUniverse);
	}

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected BaseDndDomainService<Universe> baseDndDomainService() {
		return universeDomainService;
	}

	@Override
	protected BaseFileMapper<Universe, UniverseFolder> dndMapper() {
		return universeMapper;
	}

	@Override
	protected BaseDndAdapter<UniverseFolder> folderAdapter() {
		return universeFolderAdaptor;
	}
}
