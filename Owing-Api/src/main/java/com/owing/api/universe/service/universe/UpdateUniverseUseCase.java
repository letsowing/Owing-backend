package com.owing.api.universe.service.universe;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDomainService;
import com.owing.entity.domains.universe.service.UniverseFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateUniverseUseCase extends UpdateFileUseCase<Universe, UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseDomainService baseDndDomainService;
	private final UniverseFolderDomainService universeFolderBaseDndDomainService;
	private final UniverseMapper dndMapper;


	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse execute(Long universeId, UpdateUniverseRequest updateUniverseRequest) {

		Universe oldUniverse = baseDndDomainService.getEntity(universeId);
		Universe newUniverse = dndMapper.toEntity(oldUniverse, updateUniverseRequest);
		Universe updatedUniverse = baseDndDomainService.updateUniverse(oldUniverse, newUniverse);
		return dndMapper.toInfoResponse(updatedUniverse);
	}

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected BaseDndDomainService<Universe> baseDndDomainService() {
		return baseDndDomainService;
	}

	@Override
	protected BaseDndDomainService<UniverseFolder> fBaseDndDomainService() {
		return universeFolderBaseDndDomainService;
	}

	@Override
	protected BaseFileMapper<Universe, UniverseFolder> dndMapper() {
		return dndMapper;
	}
}
