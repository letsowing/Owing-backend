package com.owing.api.universe.service.universe;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.service.UniverseService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseUseCase extends DeleteFileUseCase<Universe> {

	private final MemberUtils memberUtils;
	private final UniverseService universeDomainService;
	private final TrashCanDomainService trashCanDomainService;
	private final TrashCanMapper trashCanMapper;
	private final UniverseAdapter universeAdapter;

	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndAdapter<Universe> fileAdapter() {
		return universeAdapter;
	}

	@Override
	protected DndService<Universe> fileService() {
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
