package com.owing.api.universe.service.dnd;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.model.mapper.BaseFileMapper;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniverseCrudCrudService extends DndFileCrudService<Universe, UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseService universeService;
	private final UniverseFolderAdapter universeFolderAdaptor;
	private final UniverseMapper universeMapper;
	private final TrashCanDomainService trashCanDomainService;
	private final TrashCanMapper trashCanMapper;
	private final UniverseAdapter universeAdapter;

	@Transactional
	public UniverseShortInfoResponse create(AddUniverseRequest addUniverseRequest) {
		UniverseFolder universeFolder = universeFolderAdaptor.findById(addUniverseRequest.folderId());
		Universe universe = universeMapper.toEntity(addUniverseRequest, universeFolder);
		Universe savedUniverse = universeService.create(universe);
		return universeMapper.toInfoResponse(savedUniverse);
	}

	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse update(Long universeId, UpdateUniverseRequest updateUniverseRequest) {

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
	protected DndAdapter<UniverseFolder> folderAdapter() {
		return universeFolderAdaptor;
	}

	@Override
	protected DndAdapter<Universe> fileAdapter() {
		return universeAdapter;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}

	@Override
	protected TrashCanMapper trashCanMapper() {
		return trashCanMapper;
	}
}
