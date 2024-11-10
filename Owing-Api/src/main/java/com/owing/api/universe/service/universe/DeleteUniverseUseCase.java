package com.owing.api.universe.service.universe;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseUseCase extends DeleteFileUseCase<Universe, UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseDomainService universeDomainService;
	private final UniverseAdaptor universeAdaptor;

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected BaseDndDomainService<Universe> baseDndDomainService() {
		return universeDomainService;
	}

	// @Transactional
	// public void execute(Long universeId) {
	//
	// 	Universe universe = universeAdaptor.findById(universeId);
	// 	// todo: 유저 삭제 권한 검증
	// 	universeDomainService.deleteUniverse(universe);
	// }
}
