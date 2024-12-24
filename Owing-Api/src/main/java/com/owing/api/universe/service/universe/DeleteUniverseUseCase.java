package com.owing.api.universe.service.universe;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseUseCase extends DeleteFileUseCase<Universe> {

	private final MemberUtils memberUtils;
	private final UniverseDomainService universeDomainService;
	private final TrashCanDomainService trashCanDomainService;
	private final TrashCanMapper trashCanMapper;

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndDomainService<Universe> baseDndDomainService() {
		return universeDomainService;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}

	@Override
	protected TrashCanMapper trashCanMapper() {
		return trashCanMapper;
	}

	// @Transactional
	// public void execute(Long universeId) {
	//
	// 	Universe universe = universeAdaptor.findById(universeId);
	// 	// todo: 유저 삭제 권한 검증
	// 	universeDomainService.deleteUniverse(universe);
	// }
}
