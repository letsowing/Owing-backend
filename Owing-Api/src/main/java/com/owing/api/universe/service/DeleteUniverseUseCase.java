package com.owing.api.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.universe.adaptor.UniverseAdaptor;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.service.UniverseDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseUseCase {

	private final UniverseDomainService universeDomainService;
	private final UniverseAdaptor universeAdaptor;

	@Transactional
	public void execute(Long universeId) {

		Universe universe = universeAdaptor.findById(universeId);
		// todo: 유저 삭제 권한 검증
		universeDomainService.deleteUniverse(universe);
	}
}
