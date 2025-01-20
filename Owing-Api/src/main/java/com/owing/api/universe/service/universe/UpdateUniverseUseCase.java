package com.owing.api.universe.service.universe;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateUniverseUseCase extends UpdateFileUseCase<Universe, UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseService universeService;
	private final UniverseMapper universeMapper;
	private final UniverseAdapter universeAdapter;
	private final UniverseFolderAdapter universeFolderAdapter;

	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse execute(Long universeId, UpdateUniverseRequest updateUniverseRequest) {

		Universe oldUniverse = universeAdapter.findById(universeId);
		Universe newUniverse = universeMapper.toEntity(oldUniverse, updateUniverseRequest);
		Universe updatedUniverse = universeService.update(oldUniverse, newUniverse);
		return universeMapper.toInfoResponse(updatedUniverse);
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
	protected DndAdapter<Universe> fileAdapter() {
		return universeAdapter;
	}

	@Override
	protected DndAdapter<UniverseFolder> folderAdapter() {
		return universeFolderAdapter;
	}

}
