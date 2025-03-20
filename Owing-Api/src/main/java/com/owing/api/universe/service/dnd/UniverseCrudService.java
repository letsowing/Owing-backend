package com.owing.api.universe.service.dnd;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.api.dnd.mapper.DndFileMapper;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.model.mapper.UniverseMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseDndService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniverseCrudService extends DndFileCrudService<Universe, UniverseFolder> {

	private final MemberUtils memberUtils;
	private final UniverseFolderAdapter universeFolderAdaptor;
	private final UniverseMapper universeMapper;
	private final TrashCanDomainService trashCanDomainService;
	private final UniverseAdapter universeAdapter;
	private final UniverseDndService universeDndService;

	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse create(AddUniverseRequest addUniverseRequest) {
		UniverseFolder universeFolder = universeFolderAdaptor.findById(addUniverseRequest.folderId());
		Universe universe = universeMapper.toEntity(addUniverseRequest, universeFolder);
		Universe savedUniverse = universeDndService.create(universe);
		return universeMapper.toInfoResponse(savedUniverse);
	}

	@Transactional("jpaTransactionManager")
	public UniverseShortInfoResponse update(Long universeId, UpdateUniverseRequest updateUniverseRequest) {
		Universe oldUniverse = universeAdapter.findById(universeId);
		Universe newUniverse = universeMapper.toEntity(oldUniverse, updateUniverseRequest);
		Universe updatedUniverse = oldUniverse.update(newUniverse);
		return universeMapper.toInfoResponse(updatedUniverse);
	}

	@Override
	protected DndService<Universe> dndService() {
		return universeDndService;
	}

	@Override
	protected DndFileMapper<Universe, UniverseFolder> fileMapper() {
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
}
